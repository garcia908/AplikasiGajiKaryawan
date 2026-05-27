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

@WebServlet(name = "KaryawanController", urlPatterns = {"/KaryawanController"})
public class KaryawanController extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("userLogin") == null) {
            response.sendRedirect("LoginController");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "lihat";

        Karyawan karyawan = new Karyawan();
        Enkripsi enkripsi = new Enkripsi();

        if (action.equals("lihat")) {
            if (karyawan.bacaData()) {
                request.setAttribute("listKaryawan", karyawan.getList());
            } else {
                request.setAttribute("pesan", karyawan.getPesan());
            }
            request.getRequestDispatcher("karyawan.jsp")
                .forward(request, response);

        } else if (action.equals("simpan")) {
            String ktp = request.getParameter("ktp");
            String nama = request.getParameter("nama");
            String ruang = request.getParameter("ruang");
            String password = request.getParameter("password");

            karyawan.setKtp(ktp);
            karyawan.setNama(nama);
            karyawan.setRuang(Integer.parseInt(ruang));
            try {
                karyawan.setPassword(enkripsi.hashMD5(password));
            } catch (Exception ex) {
                karyawan.setPassword("");
            }

            if (karyawan.simpan()) {
                request.setAttribute("pesan", "Data berhasil disimpan!");
            } else {
                request.setAttribute("pesan", karyawan.getPesan());
            }
            karyawan.bacaData();
            request.setAttribute("listKaryawan", karyawan.getList());
            request.getRequestDispatcher("karyawan.jsp")
                .forward(request, response);

        } else if (action.equals("hapus")) {
            String ktp = request.getParameter("ktp");
            if (karyawan.hapus(ktp)) {
                request.setAttribute("pesan", "Data berhasil dihapus!");
            } else {
                request.setAttribute("pesan", karyawan.getPesan());
            }
            karyawan.bacaData();
            request.setAttribute("listKaryawan", karyawan.getList());
            request.getRequestDispatcher("karyawan.jsp")
                .forward(request, response);
             } else if (action.equals("edit")) {
            String ktp = request.getParameter("ktp");
            if (karyawan.baca(ktp)) {
                request.setAttribute("editKaryawan", karyawan);
            }
            karyawan.bacaData();
            request.setAttribute("listKaryawan", karyawan.getList());
            request.getRequestDispatcher("karyawan.jsp")
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