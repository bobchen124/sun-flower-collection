package com.sun.flower.support.http;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Provides a convenient implementation of the HttpServletResponse interface that
 * can be subclassed by developers wishing to adapt the response from a Servlet.
 * This class implements the Wrapper or Decorator pattern. Methods default to
 * calling through to the wrapped response object.
 * @Author: chenbo
 * @Date: 2019/4/2 17:27
 **/
public class HttpResponseWrapper extends HttpServletResponseWrapper {

    private final ServletOutputStream outputStream = new ServletOutputStreamWrapper();

    private final ByteArrayOutputStream content = new ByteArrayOutputStream();

    private PrintWriter writer;

    public HttpResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void sendError(int sc) throws IOException {
        copyBodyToResponse();
        try {
            super.sendError(sc);
        } catch (IllegalStateException ex) {
            super.setStatus(sc);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void sendError(int sc, String msg) throws IOException {
        copyBodyToResponse();
        try {
            super.sendError(sc, msg);
        } catch (IllegalStateException ex) {
            // Possibly on Tomcat when called too late: fall back to silent setStatus
            super.setStatus(sc, msg);
        }
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        copyBodyToResponse();
        super.sendRedirect(location);
    }


    @Override
    public PrintWriter getWriter() throws IOException {
        if (this.writer == null) {
            String characterEncoding = getCharacterEncoding();
            this.writer = (characterEncoding != null ? new ResponsePrintWriter(characterEncoding) : new ResponsePrintWriter("UTF-8"));
        }

        return this.writer;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return this.outputStream;
    }

    @Override
    public void resetBuffer() {
        this.content.reset();
    }

    @Override
    public void reset() {
        super.reset();
        this.content.reset();
    }

    public String getContent(String charset) {
        try {
            return content.toString(charset);
        } catch (Exception e) {
            return null;
        }
    }

    public long getContentLength(){
        return content.size();
    }

    public void copyBodyToResponse() throws IOException {
        if (this.content.size() > 0) {
            getResponse().getOutputStream().write(this.content.toByteArray());
            this.content.reset();
        }
    }

    private class ServletOutputStreamWrapper extends ServletOutputStream {

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }

        @Override
        public void write(int b) throws IOException {
            content.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            content.write(b, off, len);
        }
    }

    private class ResponsePrintWriter extends PrintWriter {

        public ResponsePrintWriter(String characterEncoding) throws UnsupportedEncodingException {
            super(new OutputStreamWriter(content, characterEncoding));
        }

        @Override
        public void write(char buf[], int off, int len) {
            super.write(buf, off, len);
            super.flush();
        }

        @Override
        public void write(String s, int off, int len) {
            super.write(s, off, len);
            super.flush();
        }

        @Override
        public void write(int c) {
            super.write(c);
            super.flush();
        }
    }

}
