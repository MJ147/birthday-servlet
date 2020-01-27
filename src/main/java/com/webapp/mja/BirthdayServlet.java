package com.webapp.mja;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

@WebServlet(
        name = "BirthdayServlet",
        description = "Check how many days left to your birthday",
        urlPatterns = "/birthday"
)
public class BirthdayServlet extends HttpServlet {

    private String firstName;
    private String secondName;
    private LocalDate birthDate;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        firstName = req.getParameter("first_name");
        secondName = req.getParameter("second_name");

        try {
            birthDate = LocalDate.parse(req.getParameter("birth_date")).withYear(LocalDate.now().getYear());
            String birthdayInformation = checkBirthDate();
            resp.getWriter().write(birthdayInformation);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            resp.getWriter().write("Wrong date format. Correct format is 'YYYY-MM-dd'");
        } catch (NullPointerException e) {
            e.printStackTrace();
            resp.getWriter().write("Enter birth date. Correct format is 'YYYY-MM-dd'");
        }

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
            String s = checkIfIsOneDay(daysToBirthday);
            birthdayInformation.append("your birthday is in ").append(daysToBirthday).append(" day").append(s).append(".");
        }
        return birthdayInformation.toString();
    }

    private String checkIfIsOneDay(String daysToBirthday) {

        String s = "s";
        if ("1".equals(daysToBirthday)) {
            s = "";
        }
        return s;
    }
}
