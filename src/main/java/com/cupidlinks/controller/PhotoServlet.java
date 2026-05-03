package com.cupidlinks.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Controller for serving uploaded profile photos from the local upload folder.
 */
@WebServlet("/uploads/*")
public class PhotoServlet extends HttpServlet {

    /**
     * Streams the requested uploaded image file to the browser.
     *
     * @param request HTTP request containing the upload file path
     * @param response HTTP response used to send the image content
     * @throws ServletException if the servlet container cannot process the request
     * @throws IOException if reading the file or writing the response fails
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fileName = request.getPathInfo().substring(1);
        String uploadDir = System.getProperty("user.home") + File.separator + "cupidlinks_uploads";
        File file = new File(uploadDir, fileName);

        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mimeType = getServletContext().getMimeType(file.getName());
        if (mimeType == null) mimeType = "image/jpeg";

        response.setContentType(mimeType);
        response.setContentLengthLong(file.length());
        Files.copy(file.toPath(), response.getOutputStream());
    }
}
