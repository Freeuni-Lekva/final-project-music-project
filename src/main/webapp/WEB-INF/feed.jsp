
<%@ page import="org.freeuni.musicforum.Activity.ActivityLog" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="org.freeuni.musicforum.model.FeedCard" %>
<%@ page import="org.freeuni.musicforum.model.Status" %>
<%@ page import="org.freeuni.musicforum.service.ServiceFactory" %>
<%@ page import="org.freeuni.musicforum.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Feed</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/main_styles.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/feedCardStyles.css" />
</head>
<body class = "background">
    <%@include file="upper_strip.jsp"%>
    <p class="space"></p>
    <%@include file="searchbar.jsp"%>
    <%  ActivityLog activity = (ActivityLog) request.getServletContext().getAttribute("activity");
        LinkedList<FeedCard> logs = activity.getLogs();
        int logSize = logs.size();
    %>

    <% for(int i = logSize - 1; i >= 0; i--) { request.setAttribute("i", i); %>

            <c:set var="currLog" value="${activity.getLogs().get(i)}" scope="request"></c:set>

            <c:if test="${currLog.type() == newAlbum}">
                <div class="feed_card">
                    <c:set var="currAlbum" value="${albumService.getAlbum(currLog.id())}" scope="request"></c:set>
                    <c:set var="albumUploader" value="${userService.getProfileData(currAlbum.username())}" scope="request"></c:set>
                    <div class="feed_card_uploader_wrapper">

                        <input type="image" src="${imagePrefix}${albumUploader.profileImageBase64()}" class="feed_card_uploader_image">
                        <% PublicUserData pdata = (PublicUserData) request.getAttribute("albumUploader");
                        if(ServiceFactory.getUserService().isActive(pdata.username())){%>
                        <a href="/profile?username=${albumUploader.username()}" class="feed_card_uploader">
                                ${albumUploader.username()}
                        </a>
                        <%} else {%>
                        <label class="feed_card_uploader"> ${albumUploader.username()} </label>
                        <%}%>

                        <label class="feed_card_uploader_date"> Uploaded at: ${currAlbum.uploadDate()} </label>
                    </div>
                    <label class="feed_card_type"> Added New Album ! </label>
                    <input type="image" src="${imagePrefix}${currAlbum.coverImageBase64()}" class="feed_card_album_image">
                    <label class="feed_card_information"> Artist: ${currAlbum.artistName()} </label>
                    <label class="feed_card_information"> Album: ${currAlbum.albumName()} </label>
                    <a href="/album?albumId=${currAlbum.id()}" class="feed_card_preview"> Preview Album! </a>
                </div>

            </c:if>
    <% } %>

</body>
</html>
