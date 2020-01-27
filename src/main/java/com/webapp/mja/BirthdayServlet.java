package com.webapp.mja;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@WebServlet(
        name = "BirthdayServlet",
        description = "Check how many days left to your birthday",
        urlPatterns = "/birthday"
)
public class BirthdayServlet extends HttpServlet {

    String firstName;
    String secondName;
    LocalDate birthDate;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        firstName = req.getParameter("first_name");
        secondName = req.getParameter("second_name");
        birthDate = LocalDate.parse(req.getParameter("birth_date")).withYear(LocalDate.now().getYear());

        String birthdayInformation = checkBirthDate();
        resp.getWriter().write(birthdayInformation);
    }

    private String checkBirthDate() {

        StringBuilder birthdayInformation = new StringBuilder("Hello " + firstName + " " + secondName + ", ");

        if (LocalDate.now().equals(birthDate)) {
            birthdayInformation.append("Happy Birthday!");
        } else {
            if (birthDate.isBefore(LocalDate.now())) {
                birthDate = birthDate.plusYears(1);
            }
            String daysToBirthday = String.valueOf(ChronoUnit.DAYS.between(LocalDate.now(), birthDate));
            birthdayInformation.append("your birthday is in " + daysToBirthday + " days.");
        }
        return birthdayInformation.toString();
    }
}
