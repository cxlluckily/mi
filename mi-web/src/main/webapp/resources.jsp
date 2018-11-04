<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" pageEncoding="UTF-8" %>
<%@ page import="com.shankephone.mi.util.CookieUtils" %>
<% response.setStatus(HttpServletResponse.SC_OK);%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

    String defaultProfile = "crisp";
    String profile = CookieUtils.getCookieValue(request, "profile");
    if (profile == null || "".equals(profile)) {
        profile = defaultProfile;
        Cookie cookie = new Cookie("profile", profile);
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

	/* String userName = null;
	String realName = null;
	Long userId = null;
	String userType = null;
	Long operationSubjectId = null;
	String userKey = SessionMap.getString("userKey");
	if(userKey != null && !"".equals(userKey)){
		userId = Long.parseLong(SessionMap.getString("userId"));
		userName = SessionMap.getString("userName");
		realName = SessionMap.getString("realName");
		String subjectId = (String)SessionMap.getString("operationSubjectId");
		if(subjectId != null && !"".equals(subjectId)){
			operationSubjectId = Long.parseLong(subjectId);
		}
		userType = SessionMap.getString("userType");
	}   */

    String userKey = (String) session.getAttribute("userKey");
    Long userId = (Long) session.getAttribute("userId");
    String userName = (String) session.getAttribute("userName");
    String realName = (String) session.getAttribute("realName");
    Long operationSubjectId = (Long) session.getAttribute("operationSubjectId");
    String userType = (String) session.getAttribute("userType");
    String functionTree = (String) session.getAttribute("functionTree");

	/* String userKey = (String)session.getAttribute("userKey");
	Long userId = (Long)session.getAttribute("userId");
	String userName = (String)session.getAttribute("userName");
	Long operationSubjectId = (Long)session.getAttribute("operationSubjectId");
	String userType = (String)session.getAttribute("userType");
	String functionTree = (String)session.getAttribute("functionTree"); */

	/* String userKey = CookieUtils.getCookieValue(request, "userKey");
	String userId = CookieUtils.getCookieValue(request, "userId");
	String userName = CookieUtils.getCookieValue(request, "userName");
	String operationSubjectId = CookieUtils.getCookieValue(request, "operationSubjectId");
	String userType = CookieUtils.getCookieValue(request, "userType"); */


%>
<link rel="stylesheet" href="app/ext/<%=profile %>/resources/KitchenSink-all_1.css"/>
<link rel="stylesheet" href="app/ext/<%=profile %>/resources/KitchenSink-all_2.css"/>
<link rel="stylesheet" href="app/ext/packages/ux/classic/<%=profile %>/resources/ux-all.css"/>
<link rel='stylesheet' href='app/ext/resources/Sencha-Examples/style.css'/>
<link rel='stylesheet' href='app/resources/css/app.css'/>
<script type='text/javascript' src='app/ext/ext-all.js'></script>
<script type='text/javascript' src="app/resources/js/lib/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="app/ext/<%=profile %>/theme.js"></script>
<script type='text/javascript' src='app/ext/packages/ux/classic/ux.js'></script>
<script type='text/javascript' src='app/ext/locale-zh_CN.js'></script>
<script type='text/javascript' src='app/common/ajax.js'></script>
<script type='text/javascript' src='app/common/common.js'></script>
<script type='text/javascript' src='app/app.js'></script>
<script type='text/javascript' src='app/resources/js/src-noconflict/ace.js'></script>


<script type="text/javascript">
    function isNull(tmp) {
        return !tmp && typeof(tmp) != "undefined" && tmp != 0;
    }

    var ctx = '<%=path%>' + '/';
    // var ctx = 'http://192.168.1.200:8082/mi/';
    var userKey = '<%=userKey%>';
    var userName = '<%=userName%>';
    var realName = '<%=realName%>';
    var userId = '<%=userId%>';
    var userType = '<%=userType%>';
    var operationSubjectId = '<%=operationSubjectId%>';
    // if(userKey!=='null'){
    window.sessionStorage["userKey"] = userKey;
    window.sessionStorage["operationSubjectId"] = operationSubjectId;
    window.sessionStorage["userName"] = userName;
    window.sessionStorage["realName"] = realName;
    window.sessionStorage["userId"] = userId;
    if (userType !== 'null') {
        window.sessionStorage["userType"] = userType;
    }
    // }

</script>
