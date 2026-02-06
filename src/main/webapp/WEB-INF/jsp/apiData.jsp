<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Réservations</title>
</head>
<body>
    <h1>Liste des Réservations</h1>

    <form action="/api-data" method="get">
        <label for="date">Filtrer par Date d'Arrivée (YYYY-MM-DD):</label>
        <input type="text" id="date" name="date" value="<%= request.getAttribute("filterDate") != null ? request.getAttribute("filterDate") : "" %>" placeholder="2023-10-01">
        <button type="submit">Filtrer</button>
    </form>

    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>IdClient</th>
                <th>Nb Passager</th>
                <th>Date Heure Arrive</th>
                <th>IdHotel</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Map<String, Object>> reservations = (List<Map<String, Object>>) request.getAttribute("reservations");
                if (reservations != null) {
                    for (Map<String, Object> reservation : reservations) {
            %>
            <tr>
                <td><%= reservation.get("id") %></td>
                <td><%= reservation.get("idClient") %></td>
                <td><%= reservation.get("nbPassager") %></td>
                <td><%= reservation.get("dateHeureArrive") %></td>
                <td><%= reservation.get("idHotel") %></td>
            </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>