
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
<body class = "long_background">
    <%@include file="upper_strip.jsp"%>
    <p class="space"></p>
    <%@include file="searchbar.jsp"%>
    <%  ActivityLog activity = (ActivityLog) request.getServletContext().getAttribute("activity");
        LinkedList<FeedCard> logs = activity.getLogs();
        int logSize = logs.size();
    %>

    <% for(int i = logSize - 1; i >= 0; i--) { request.setAttribute("i", i); %>
        <c:set var="currLog" value="${activity.getLogs().get(i)}" scope="request"></c:set>
        <% if(logs.get(i).type() == ActivityLog.ActivityType.NEW_ALBUM) { %>
            <% if(!ServiceFactory.getAlbumService().exists(((FeedCard)request.getAttribute("currLog")).id()))
            {
                continue;
            }%>
            <c:set var="reviewer" value="" scope="request"></c:set>
            <c:set var="currAlbum" value="${albumService.getAlbum(currLog.id())}" scope="request"></c:set>
            <c:set var="type" value="Album" scope="request"></c:set>
        <% } else { %>
            <% if(ServiceFactory.getReviewService().getReview(((FeedCard)request.getAttribute("currLog")).id()) == null)
            {
                continue;
            }%>
            <c:set var="review" value="${reviewService.getReview(currLog.id())}" scope="request"></c:set>
            <c:set var="reviewer" value="${review.getAuthorUsername()}" scope="request"></c:set>
            <c:set var="reviewerData" value="${userService.getProfileData(reviewer)}" scope="request"></c:set>
            <c:set var="reviewerImage" value="${reviewerData.profileImageBase64()}" scope="request"></c:set>
            <c:set var="reviewPostedAt" value="Posted At ${review.getUploadDate()}" scope="request"></c:set>
            <c:set var="reviewContent" value="${review.getText()}" scope="request"></c:set>
            <c:set var="currAlbum" value="${albumService.getAlbum(review.getAlbumId())}" scope="request"></c:set>
            <c:set var="type" value="Review" scope="request"></c:set>
        <% } %>
        <c:set var="albumUploader" value="${userService.getProfileData(currAlbum.username())}" scope="request"></c:set>
        <div class="feed_card">
            <div class="feed_card_uploader_wrapper">
                <img src="${imagePrefix}${albumUploader.profileImageBase64()}" class="feed_card_uploader_image"/>
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
            <label class="feed_card_type"> New ${type} Posted! </label>
            <img src="${imagePrefix}${currAlbum.coverImageBase64()}" class="feed_card_album_image"/>
            <label class="feed_card_album_information"> Artist: ${currAlbum.artistName()} </label>
            <label class="feed_card_album_information"> Album: ${currAlbum.albumName()} </label>
            <c:if test="${!reviewer.equals(\"\")}">
                <div class="feed_card_reviewer_wrapper">
                    <img src="${imagePrefix}${reviewerImage}" class="feed_card_reviewer_image"/>
                    <% String reviewerUsername = (String) request.getAttribute("reviewer");
                    if(ServiceFactory.getUserService().isActive(reviewerUsername)){%>
                        <a href="/profile?username=${reviewer}" class="feed_card_reviewer">
                            ${reviewer}
                        </a>
                    <%} else {%>
                        <label class="feed_card_reviewer"> ${reviewer} </label>
                    <%}%>
                </div>
                <div class="feed_card_review_wrapper">
                    <label class="feed_card_review_content"> ${reviewContent}</label>
                    <label class="feed_card_review_date"> ${reviewPostedAt}</label>
                </div></c:if>
                <a href="/album?albumId=${currAlbum.id()}" class="feed_card_preview"> Preview Album! </a>
        </div>
    <% } %>

</body>
</html>
