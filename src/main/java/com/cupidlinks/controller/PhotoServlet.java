package com.cupidlinks.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controller for serving uploaded profile photos from the webapp upload folder.
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

        if (request.getPathInfo() == null || request.getPathInfo().length() <= 1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Path uploadPath = getUploadDir();
        String fileName = Paths.get(request.getPathInfo()).getFileName().toString();
        Path filePath = uploadPath.resolve(fileName).normalize();

        if (!filePath.startsWith(uploadPath) || !Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mimeType = getServletContext().getMimeType(filePath.getFileName().toString());
        if (mimeType == null) mimeType = "image/jpeg";

        response.setContentType(mimeType);
        response.setContentLengthLong(Files.size(filePath));
        Files.copy(filePath, response.getOutputStream());
    }

    private Path getUploadDir() throws IOException {
        Path uploadDir = findSourceUploadDir();
        Files.createDirectories(uploadDir);
        return uploadDir;
    }

    private Path findSourceUploadDir() {
        Path fromWorkingDir = findProjectUploadDir(Paths.get(System.getProperty("user.dir")));
        if (fromWorkingDir != null) {
            return fromWorkingDir;
        }

        String deployedRoot = getServletContext().getRealPath("/");
        if (deployedRoot != null) {
            Path fromDeployment = findProjectUploadDir(Paths.get(deployedRoot));
            if (fromDeployment != null) {
                return fromDeployment;
            }
        }

        return Paths.get(getServletContext().getRealPath("/uploads")).toAbsolutePath().normalize();
    }

    private Path findProjectUploadDir(Path start) {
        Path current = start.toAbsolutePath().normalize();
        while (current != null) {
            Path webappDir = current.resolve(Paths.get("src", "main", "webapp"));
            if (Files.isDirectory(webappDir)) {
                return webappDir.resolve("uploads").toAbsolutePath().normalize();
            }
            current = current.getParent();
        }
        return null;
    }
}
