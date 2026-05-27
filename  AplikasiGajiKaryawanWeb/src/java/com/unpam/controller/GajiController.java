package com.unpam.controller;

import com.unpam.model.Gaji;
import com.unpam.model.Karyawan;
import com.unpam.model.Pekerjaan;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "GajiController", urlPatterns = {"/GajiController"})
public class GajiController extends HttpServlet {

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

        Gaji gaji = new Gaji();
        Karyawan karyawan = new Karyawan();
        Pekerjaan pekerjaan = new Pekerjaan();

        if (action.equals("lihat")) {
            String ktp = request.getParameter("ktp");
            if (ktp != null && !ktp.equals("")) {
                karyawan.baca(ktp);
                request.setAttribute("karyawan", karyawan);
                gaji.bacaData(ktp);
                request.setAttribute("listGaji", gaji.getList());
            }
            pekerjaan.bacaData();
            request.setAttribute("listPekerjaan", pekerjaan.getList());
            request.getRequestDispatcher("gaji.jsp")
                .forward(request, response);

        } else if (action.equals("simpan")) {
            String ktp = request.getParameter("ktp");
            String[] kodes = request.getParameterValues("kodepekerjaan");
            String[] bersih = request.getParameterValues("gajibersih");
            String[] kotor = request.getParameterValues("gajikotor");
            String[] tunjangans = request.getParameterValues("tunjangan");

            if (kodes != null) {
                if (gaji.simpan(ktp, kodes, bersih, kotor, tunjangans)) {
                    request.setAttribute("pesan", "Data gaji berhasil disimpan!");
                } else {
                    request.setAttribute("pesan", gaji.getPesan());
                }
            }
            karyawan.baca(ktp);
            request.setAttribute("karyawan", karyawan);
            gaji.bacaData(ktp);
            request.setAttribute("listGaji", gaji.getList());
            pekerjaan.bacaData();
            request.setAttribute("listPekerjaan", pekerjaan.getList());
            request.getRequestDispatcher("gaji.jsp")
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