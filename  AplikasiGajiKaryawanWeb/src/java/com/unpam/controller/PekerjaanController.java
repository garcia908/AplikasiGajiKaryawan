package com.unpam.controller;

import com.unpam.model.Pekerjaan;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "PekerjaanController", urlPatterns = {"/PekerjaanController"})
public class PekerjaanController extends HttpServlet {

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

        Pekerjaan pekerjaan = new Pekerjaan();

        if (action.equals("lihat")) {
            if (pekerjaan.bacaData()) {
                request.setAttribute("listPekerjaan", pekerjaan.getList());
            } else {
                request.setAttribute("pesan", pekerjaan.getPesan());
            }
            request.getRequestDispatcher("pekerjaan.jsp")
                .forward(request, response);

        } else if (action.equals("simpan")) {
            String kode = request.getParameter("kodepekerjaan");
            String nama = request.getParameter("namapekerjaan");
            String jumlah = request.getParameter("jumlahtugas");

            pekerjaan.setKodePekerjaan(kode);
            pekerjaan.setNamaPekerjaan(nama);
            pekerjaan.setJumlahTugas(Integer.parseInt(jumlah));

            if (pekerjaan.simpan()) {
                request.setAttribute("pesan", "Data berhasil disimpan!");
            } else {
                request.setAttribute("pesan", pekerjaan.getPesan());
            }
            pekerjaan.bacaData();
            request.setAttribute("listPekerjaan", pekerjaan.getList());
            request.getRequestDispatcher("pekerjaan.jsp")
                .forward(request, response);

        } else if (action.equals("hapus")) {
            String kode = request.getParameter("kode");
            if (pekerjaan.hapus(kode)) {
                request.setAttribute("pesan", "Data berhasil dihapus!");
            } else {
                request.setAttribute("pesan", pekerjaan.getPesan());
            }
            pekerjaan.bacaData();
            request.setAttribute("listPekerjaan", pekerjaan.getList());
            request.getRequestDispatcher("pekerjaan.jsp")
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