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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css?v=4">
</head>
<body>

<c:set var="activePage" value="profile" scope="request"/>
<jsp:include page="/WEB-INF/views/shared/user-navbar.jsp"/>
<c:set var="editMode" value="${empty profile or param.edit eq 'true' or not empty error}" scope="page"/>

<div class="page-wrapper">
    <div class="profile-form-card">

        <div class="profile-form-header">
            <h2>${empty profile ? 'Set Up Your Profile' : editMode ? 'Edit Your Profile' : 'My Profile'}</h2>
            <p>${empty profile ? 'Fill in your details so others can discover you.' : editMode ? 'Keep your profile up to date.' : 'View your current profile details.'}</p>
        </div>

        <c:if test="${param.saved eq 'true'}">
            <div class="alert alert-success">Profile saved successfully!</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-error"><c:out value="${error}"/></div>
        </c:if>

        <c:if test="${not editMode}">
            <section class="profile-view">
                <div class="profile-view-head">
                    <div class="profile-view-photo">
                        <c:choose>
                            <c:when test="${not empty profile.profilePhoto}">
                                <img src="${pageContext.request.contextPath}/uploads/${profile.profilePhoto}" alt="Your photo">
                            </c:when>
                            <c:otherwise>
                                <span>CL</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="profile-view-title">
                        <h3><c:out value="${profile.fullName}"/></h3>
                        <p><c:out value="${not empty profile.location ? profile.location : 'Location not added'}"/></p>
                    </div>
                    <a href="${pageContext.request.contextPath}/profile?edit=true" class="btn-primary profile-edit-btn">Edit Profile</a>
                </div>

                <div class="profile-detail-grid">
                    <div><span>Date of Birth</span><strong><c:out value="${profile.dateOfBirth}"/></strong></div>
                    <div><span>Gender</span><strong><c:out value="${profile.gender}"/></strong></div>
                    <div><span>Looking For</span><strong><c:out value="${profile.datingPreference}"/></strong></div>
                    <div><span>Visibility</span><strong><c:out value="${profile.visibility}"/></strong></div>
                    <div><span>Nepali Raasi</span><strong><c:out value="${not empty profile.nepaliRaasi ? profile.nepaliRaasi : 'Not added'}"/></strong></div>
                    <div><span>Clan Lineage</span><strong><c:out value="${not empty profile.clan ? profile.clan : 'Not added'}"/></strong></div>
                </div>

                <div class="profile-view-section">
                    <span>Bio</span>
                    <p><c:out value="${not empty profile.bio ? profile.bio : 'No bio added yet.'}"/></p>
                </div>

                <div class="profile-view-section">
                    <span>Interests</span>
                    <div class="profile-interest-list">
                        <c:choose>
                            <c:when test="${not empty selectedInterests}">
                                <c:forEach var="interest" items="${selectedInterests}">
                                    <strong><c:out value="${interest}"/></strong>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p>No interests selected yet.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </section>
        </c:if>

        <c:if test="${editMode}">
            <c:if test="${not empty profile.profilePhoto}">
                <div class="current-photo">
                    <img src="${pageContext.request.contextPath}/uploads/${profile.profilePhoto}" alt="Your photo">
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
                        <select id="datingPreference" name="datingPreference" required>
                            <option value="">Select what you're looking for</option>
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
                            <option value="Mesh"      ${not empty profile && profile.nepaliRaasi eq 'Mesh'      ? 'selected' : ''}>Mesh</option>
                            <option value="Brish"     ${not empty profile && profile.nepaliRaasi eq 'Brish'     ? 'selected' : ''}>Brish</option>
                            <option value="Mithun"    ${not empty profile && profile.nepaliRaasi eq 'Mithun'    ? 'selected' : ''}>Mithun</option>
                            <option value="Karkat"    ${not empty profile && profile.nepaliRaasi eq 'Karkat'    ? 'selected' : ''}>Karkat</option>
                            <option value="Singh"     ${not empty profile && profile.nepaliRaasi eq 'Singh'     ? 'selected' : ''}>Singh</option>
                            <option value="Kanya"     ${not empty profile && profile.nepaliRaasi eq 'Kanya'     ? 'selected' : ''}>Kanya</option>
                            <option value="Tula"      ${not empty profile && profile.nepaliRaasi eq 'Tula'      ? 'selected' : ''}>Tula</option>
                            <option value="Brishchik" ${not empty profile && profile.nepaliRaasi eq 'Brishchik' ? 'selected' : ''}>Brishchik</option>
                            <option value="Dhanu"     ${not empty profile && profile.nepaliRaasi eq 'Dhanu'     ? 'selected' : ''}>Dhanu</option>
                            <option value="Makar"     ${not empty profile && profile.nepaliRaasi eq 'Makar'     ? 'selected' : ''}>Makar</option>
                            <option value="Kumbha"    ${not empty profile && profile.nepaliRaasi eq 'Kumbha'    ? 'selected' : ''}>Kumbha</option>
                            <option value="Meen"      ${not empty profile && profile.nepaliRaasi eq 'Meen'      ? 'selected' : ''}>Meen</option>
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
                    <label>Interests</label>
                    <div class="interest-picker">
                        <label class="interest-chip"><input type="checkbox" name="interests" value="Music" ${selectedInterests.contains('Music') ? 'checked' : ''}><span>Music</span></label>
                        <label class="interest-chip"><input type="checkbox" name="interests" value="Travel" ${selectedInterests.contains('Travel') ? 'checked' : ''}><span>Travel</span></label>
                        <label class="interest-chip"><input type="checkbox" name="interests" value="Food" ${selectedInterests.contains('Food') ? 'checked' : ''}><span>Food</span></label>
                        <label class="interest-chip"><input type="checkbox" name="interests" value="Movies" ${selectedInterests.contains('Movies') ? 'checked' : ''}><span>Movies</span></label>
                        <label class="interest-chip"><input type="checkbox" name="interests" value="Books" ${selectedInterests.contains('Books') ? 'checked' : ''}><span>Books</span></label>
                        <label class="interest-chip"><input type="checkbox" name="interests" value="Fitness" ${selectedInterests.contains('Fitness') ? 'checked' : ''}><span>Fitness</span></label>
                        <label class="interest-chip"><input type="checkbox" name="interests" value="Hiking" ${selectedInterests.contains('Hiking') ? 'checked' : ''}><span>Hiking</span></label>
                        <label class="interest-chip"><input type="checkbox" name="interests" value="Photography" ${selectedInterests.contains('Photography') ? 'checked' : ''}><span>Photography</span></label>
                        <label class="interest-chip"><input type="checkbox" name="interests" value="Dancing" ${selectedInterests.contains('Dancing') ? 'checked' : ''}><span>Dancing</span></label>
                        <label class="interest-chip"><input type="checkbox" name="interests" value="Gaming" ${selectedInterests.contains('Gaming') ? 'checked' : ''}><span>Gaming</span></label>
                        <label class="interest-chip"><input type="checkbox" name="interests" value="Art" ${selectedInterests.contains('Art') ? 'checked' : ''}><span>Art</span></label>
                        <label class="interest-chip"><input type="checkbox" name="interests" value="Spirituality" ${selectedInterests.contains('Spirituality') ? 'checked' : ''}><span>Spirituality</span></label>
                    </div>
                    <small>These tags improve compatibility scores on Discover.</small>
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
        </c:if>
    </div>
</div>

</body>
</html>
