<%--
  Created by IntelliJ IDEA.
  User: Bhumika
  Date: 4/12/2026
  Time: 7:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - CupidLinks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css?v=2">
</head>
<body>

<c:set var="activePage" value="profile" scope="request"/>
<jsp:include page="/WEB-INF/views/shared/user-navbar.jsp"/>

<div class="page-wrapper">
    <div class="profile-form-card">

        <div class="profile-form-header">
            <h2>${empty profile ? 'Set Up Your Profile' : 'Edit Your Profile'}</h2>
            <p>${empty profile ? 'Fill in your details so others can discover you.' : 'Keep your profile up to date.'}</p>
        </div>

        <c:if test="${param.saved eq 'true'}">
            <div class="alert alert-success">Profile saved successfully!</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-error"><c:out value="${error}"/></div>
        </c:if>

        <%-- show current photo if exists --%>
        <c:if test="${not empty profile.profilePhoto}">
            <div class="current-photo">
                <img src="${pageContext.request.contextPath}/uploads/<c:out value='${profile.profilePhoto}'/>" alt="Your photo">
                <span>Current photo</span>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/profile" method="post"
              class="profile-form" enctype="multipart/form-data">

            <div class="form-group">
                <label for="profilePhoto">Profile Photo</label>
                <input type="file" id="profilePhoto" name="profilePhoto" accept="image/*">
                <small>JPG or PNG, max 5MB. Leave empty to keep current photo.</small>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName" placeholder="Your full name" required
                           value="${not empty profile ? profile.fullName : ''}">
                </div>
                <div class="form-group">
                    <label for="dateOfBirth">Date of Birth</label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth" required
                           value="${not empty profile ? profile.dateOfBirth : ''}">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="gender">Gender</label>
                    <select id="gender" name="gender" required>
                        <option value="">Select gender</option>
                        <option value="male"              ${not empty profile && profile.gender eq 'male'              ? 'selected' : ''}>Male</option>
                        <option value="female"            ${not empty profile && profile.gender eq 'female'            ? 'selected' : ''}>Female</option>
                        <option value="non-binary"        ${not empty profile && profile.gender eq 'non-binary'        ? 'selected' : ''}>Non-binary</option>
                        <option value="prefer_not_to_say" ${not empty profile && profile.gender eq 'prefer_not_to_say' ? 'selected' : ''}>Prefer not to say</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="location">Location</label>
                    <input type="text" id="location" name="location" placeholder="e.g. Kathmandu"
                           value="${not empty profile ? profile.location : ''}">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="datingPreference">Looking For</label>
                    <select id="datingPreference" name="datingPreference">
                        <option value="open"       ${not empty profile && profile.datingPreference eq 'open'       ? 'selected' : ''}>Open to anything</option>
                        <option value="casual"     ${not empty profile && profile.datingPreference eq 'casual'     ? 'selected' : ''}>Casual dating</option>
                        <option value="serious"    ${not empty profile && profile.datingPreference eq 'serious'    ? 'selected' : ''}>Serious relationship</option>
                        <option value="friendship" ${not empty profile && profile.datingPreference eq 'friendship' ? 'selected' : ''}>Friendship</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="nepaliRaasi">Nepali Raasi (optional)</label>
                    <select id="nepaliRaasi" name="nepaliRaasi">
                        <option value="">Select your raasi</option>
                        <option value="Mesh"      ${not empty profile && profile.nepaliRaasi eq 'Mesh'      ? 'selected' : ''}>Mesh (मेष)</option>
                        <option value="Brish"     ${not empty profile && profile.nepaliRaasi eq 'Brish'     ? 'selected' : ''}>Brish (वृष)</option>
                        <option value="Mithun"    ${not empty profile && profile.nepaliRaasi eq 'Mithun'    ? 'selected' : ''}>Mithun (मिथुन)</option>
                        <option value="Karkat"    ${not empty profile && profile.nepaliRaasi eq 'Karkat'    ? 'selected' : ''}>Karkat (कर्कट)</option>
                        <option value="Singh"     ${not empty profile && profile.nepaliRaasi eq 'Singh'     ? 'selected' : ''}>Singh (सिंह)</option>
                        <option value="Kanya"     ${not empty profile && profile.nepaliRaasi eq 'Kanya'     ? 'selected' : ''}>Kanya (कन्या)</option>
                        <option value="Tula"      ${not empty profile && profile.nepaliRaasi eq 'Tula'      ? 'selected' : ''}>Tula (तुला)</option>
                        <option value="Brishchik" ${not empty profile && profile.nepaliRaasi eq 'Brishchik' ? 'selected' : ''}>Brishchik (वृश्चिक)</option>
                        <option value="Dhanu"     ${not empty profile && profile.nepaliRaasi eq 'Dhanu'     ? 'selected' : ''}>Dhanu (धनु)</option>
                        <option value="Makar"     ${not empty profile && profile.nepaliRaasi eq 'Makar'     ? 'selected' : ''}>Makar (मकर)</option>
                        <option value="Kumbha"    ${not empty profile && profile.nepaliRaasi eq 'Kumbha'    ? 'selected' : ''}>Kumbha (कुम्भ)</option>
                        <option value="Meen"      ${not empty profile && profile.nepaliRaasi eq 'Meen'      ? 'selected' : ''}>Meen (मीन)</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="clan">Clan lineage (optional)</label>
                <input type="text" id="clan" name="clan" placeholder="e.g. Kausika, Bharadwaj, Bajracharya"
                       value="${not empty profile ? profile.clan : ''}">
                <small>This is optional. It helps others notice clan information before deciding to connect.</small>
            </div>

            <div class="form-group">
                <label for="bio">Bio</label>
                <textarea id="bio" name="bio" placeholder="Tell others a little about yourself..." rows="4"><c:if test="${not empty profile}">${profile.bio}</c:if></textarea>
            </div>

            <div class="form-group">
                <label for="visibility">Profile Visibility</label>
                <select id="visibility" name="visibility">
                    <option value="public"       ${not empty profile && profile.visibility eq 'public'       ? 'selected' : ''}>Public - anyone can see me</option>
                    <option value="matches_only" ${not empty profile && profile.visibility eq 'matches_only' ? 'selected' : ''}>Matches only</option>
                    <option value="hidden"       ${not empty profile && profile.visibility eq 'hidden'       ? 'selected' : ''}>Hidden</option>
                </select>
            </div>

            <button type="submit" class="btn-primary">Save Profile</button>

        </form>
    </div>
</div>

</body>
</html>
