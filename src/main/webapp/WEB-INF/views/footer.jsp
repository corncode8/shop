<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
		<meta name="viewport" content="width=device-width,initial-scale=1">
        <link href="https://fonts.googleapis.com/css?family=Maven+Pro:400,500,700,900|Noto+Sans+KR:100,300,400,500,700,900" rel="stylesheet">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Libre+Baskerville:700&display=swap" rel="stylesheet">
</head>

<body>
<!--풋터-->   
    <div class="footer">
        <div class="footer__left">
            [이용약관] [개인정보처리방침] [개인정보관리책임자 : ???]<br>
            [대표 : ???] [사업자등록번호 : 000-00-00000]<br>
            [통신판매신고번호 : 2023-경남진주-00000] [사업자정보확인]
        </div>
        <div class="footer__mid">
            NMM
        </div>
        	<!-- Default dropright button -->
			<div class="btn-group dropright">
			<a href="../manager/managerLogin.jsp">관리자 로그인</a>
			  <button type="button" class="btn btn-secondary dropdown-toggle btn-link" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			  </button>
			  <div class="dropdown-menu">
			    	<a class="dropdown-item" href="${pageContext.request.contextPath}/servlet?controller=siteManage&command=selectAll">상품관리</a><br>
					<a class="dropdown-item" href="#">페이지 2 </a><br>
					<a class="dropdown-item" href="#">페이지 3 </a><br>
			  </div>
			</div>


        <div class="footer__right">
            입점문의 : korea@anderssonbell.com<br>
            문의메일 : cs@anderssonbell.com<br>
            copyright@anderssonbell.com all right reseved
    <a href="<%=application.getContextPath()%>/manager/managerLogin.jsp">관리자 로그인</a>
        </div>
    </div>
</body>
</html>