package com.main.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

@Component
public class ResponseMetadataFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        long startedAt = System.nanoTime();
        response.setHeader("X-Endpoint", request.getRequestURI());

        try {
            filterChain.doFilter(request, new HttpServletResponseWrapper(response) {
                private final AtomicBoolean metadataWritten = new AtomicBoolean();

                private void writeMetadata() {
                    if (metadataWritten.compareAndSet(false, true)) {
                        long responseTimeMs = (System.nanoTime() - startedAt) / 1_000_000;
                        setHeader("X-Endpoint", request.getRequestURI());
                        setHeader("X-Response-Time-Ms", String.valueOf(responseTimeMs));
                    }
                }

                @Override
                public void flushBuffer() throws IOException {
                    writeMetadata();
                    super.flushBuffer();
                }

                @Override
                public void sendError(int statusCode) throws IOException {
                    writeMetadata();
                    super.sendError(statusCode);
                }

                @Override
                public void sendError(int statusCode, String message) throws IOException {
                    writeMetadata();
                    super.sendError(statusCode, message);
                }

                @Override
                public void sendRedirect(String location) throws IOException {
                    writeMetadata();
                    super.sendRedirect(location);
                }

                @Override
                public void setStatus(int statusCode) {
                    writeMetadata();
                    super.setStatus(statusCode);
                }

                @Override
                public PrintWriter getWriter() throws IOException {
                    PrintWriter writer = super.getWriter();
                    return new PrintWriter(writer) {
                        @Override
                        public void flush() {
                            writeMetadata();
                            super.flush();
                        }
                    };
                }

                @Override
                public ServletOutputStream getOutputStream() throws IOException {
                    ServletOutputStream outputStream = super.getOutputStream();
                    return new ServletOutputStream() {
                        @Override
                        public boolean isReady() {
                            return outputStream.isReady();
                        }

                        @Override
                        public void setWriteListener(WriteListener listener) {
                            outputStream.setWriteListener(listener);
                        }

                        @Override
                        public void write(int value) throws IOException {
                            outputStream.write(value);
                        }

                        @Override
                        public void flush() throws IOException {
                            writeMetadata();
                            outputStream.flush();
                        }

                        @Override
                        public void close() throws IOException {
                            writeMetadata();
                            outputStream.close();
                        }
                    };
                }
            });
        } finally {
            if (!response.isCommitted()) {
                response.setHeader("X-Response-Time-Ms", String.valueOf((System.nanoTime() - startedAt) / 1_000_000));
            }
        }
    }
}
