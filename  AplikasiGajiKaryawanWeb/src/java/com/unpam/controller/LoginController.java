package com.unpam.controller;

import com.unpam.model.Enkripsi;
import com.unpam.model.Karyawan;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        if (action.equals("logout")) {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect("index.jsp");
            return;
        }

        String ktp = request.getParameter("ktp");
        String password = request.getParameter("password");

        if (ktp != null && password != null) {
            Karyawan karyawan = new Karyawan();
            Enkripsi enkripsi = new Enkripsi();

            if (karyawan.baca(ktp)) {
                try {
                    String hashedPass = enkripsi.hashMD5(password);
                    if (karyawan.getPassword().equalsIgnoreCase(hashedPass)) {
                        HttpSession session = request.getSession();
                        session.setAttribute("userLogin", karyawan.getNama());
                        session.setAttribute("ktpLogin", ktp);
                        response.sendRedirect("index.jsp");
                    } else {
                        request.setAttribute("pesan", "Password salah!");
                        request.getRequestDispatcher("login.jsp")
                            .forward(request, response);
                    }
                } catch (Exception ex) {
                    request.setAttribute("pesan", "Error: " + ex.getMessage());
                    request.getRequestDispatcher("login.jsp")
                        .forward(request, response);
                }
            } else {
                request.setAttribute("pesan", "KTP tidak ditemukan!");
                request.getRequestDispatcher("login.jsp")
                    .forward(request, response);
            }
        } else {
            request.getRequestDispatcher("login.jsp")
                .forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}