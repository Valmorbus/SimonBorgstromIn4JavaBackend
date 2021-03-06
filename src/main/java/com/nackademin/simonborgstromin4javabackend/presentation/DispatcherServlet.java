/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nackademin.simonborgstromin4javabackend.presentation;

import com.nackademin.simonborgstromin4javabackend.business.Boundary;
import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author borgs_000
 */
@WebServlet(name = "StudentServlet", urlPatterns = {"/", "/secure/DispatcherServlet"})
public class DispatcherServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Inject
    Boundary bound;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StudentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        String forward = null;

        switch (path) {
            case "/index":
                request.setAttribute("allCourses", bound.listAllCourses());
                forward = "/index.jsp";
                break;
            case "/students":
                int idCourse = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("courseName", bound.findCourse(idCourse).getNamn());
                request.setAttribute("allStudents", bound.getAllStudentFromCourse(idCourse));
                forward = "/students.jsp";
                break;
            case "/secure/betyg":
                int studentid = Integer.parseInt(request.getParameter("id"));
                System.out.println(studentid);
                request.setAttribute("student", bound.getStudentToUpdate(studentid));
                forward = "/secure/betyg.jsp";
                break;
            case "/secure/admin":
                request.setAttribute("allStudents", bound.getAllUnregisteredStudent());
                request.setAttribute("allCourses", bound.getAllUnregisteredCourses());
                request.setAttribute("allGrades", bound.getAllGrades());
                forward = "/secure/admin.jsp";
                break;
            default:
                request.setAttribute("allCourses", bound.listAllCourses());
                forward = "/index.jsp";
                break;
        }

        request.getRequestDispatcher(forward).forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String redirect = "";
        String postType = request.getParameter("postType");
        switch (postType) {
            case "setGrade":
                Integer kursId = Integer.valueOf(request.getParameter("kursid"));
                Integer studentid = Integer.valueOf(request.getParameter("studentid"));
                String requ = request.getParameter("value");
                System.out.println(kursId + " " + studentid + " " + requ);
                bound.setGrade(studentid, kursId, requ);
                break;
            case "deleteStudent":
                int studentId = Integer.parseInt(request.getParameter("studentid"));
                bound.removeStudent(studentId);
                break;
            case "deleteCourse":
                int kursToDeleteId = Integer.parseInt(request.getParameter("kursid"));
                bound.removeCourse(kursToDeleteId);
                break;
            case "addStudent":
                String studentToAdd = request.getParameter("name");
                bound.addStudent(studentToAdd);

                break;
            case "addCourse":
                String courseToadd = request.getParameter("name");
                bound.addCourse(courseToadd);
                break;
        }
        response.sendRedirect("admin");
        //  request.getRequestDispatcher("/secure/admin.jsp").forward(request, response);

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
