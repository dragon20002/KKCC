<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script src="<c:url value="/resources/js/sectionController.js" />"></script>

<header>
	<c:if test="${pageContext.request.userPrincipal.name != null }">
		<div class="float-right" style="margin: 0 0 0 0; width: 80px">
			<a class="logout" href="javascript:document.getElementById('logout').submit()"
				style="text-decoration: none;"><b>로그아웃</b></a>
		</div>
	</c:if>

	<h2 style="text-align: center">
		<a href="<c:url value="/" />" style="text-decoration: none">
			<b style="color: #fdd835">Situation-cognitive Traffic Light Controller</b>
			<br>
			<b style="color: white">상황인지 신호 제어기</b>
		</a>
	</h2>

	<hr>

	<div class="row" align="right">
		<div class="col-md-2"><b>팀명</b> KKCC</div>
		<div class="col-md-5"><b>팀원</b> 조성윤 김민우 조용훈</div>
		<div class="col-md-4"><b>지도교수</b> 김성동 교수님</div>
	</div>
</header>

<section class="dropdown" ng-app="sectionApp" ng-controller="sectionController">
	<!-- 교차로 추가 -->
	<c:if test="${pageContext.request.userPrincipal.name != null }">
		<div class="btn float-left" style="background-color: #fdd835; font-size: 20px">
			<input ng-show="inputIsVisible" ng-model="sectionName" class="form-control" type="text" placeholder="교차로 이름" style="display: inline; width: 80%; height: 60%">
			<i ng-click="showInput()" class="fa fa-plus"></i>
			<i ng-show="inputIsVisible" ng-click="hideInput()" class="fa fa-remove"></i>
		</div>
	</c:if>

	<!-- 교차로 선택 버튼 -->	
	<div class="dropdown-toggle" data-toggle="dropdown"
	 	 style="text-align: center; padding: 10px; background-color: #fdd835; font-size: 20px">
		<b>교차로 선택</b>
	</div>

	<!-- 교차로 목록 -->
	<c:if test="${pageContext.request.userPrincipal.name != null }">
		<div class="dropdown-menu" style="width: 100%; margin: auto;" ng-init="initSections()">
			<!-- default item0 -->
			<div class="dropdown-item">
				<a style="text-decoration: none; color: black" href="<c:url value="/trafficstatus/0" />">한성대 사거리&#35;0</a>
			</div>
			<div class="dropdown-item" ng-repeat="section in sections">
				<a style="text-decoration: none; color: black" href="<c:url value="/trafficstatus/{{section.id}}" />">{{section.name}}&#35;{{section.id}}</a>
				<a style="color: #DC143C; font-size: 16px; margin-left: 10px" href="">
					<i ng-click="removeSection(section.id)" class="fa fa-remove"></i>
				</a>
			</div>
	
			<hr style="width: 95%; align: center"><!-- ---------------------------------------------------------------------- -->
	
			<div class="dropdown-item">
				<a style="text-decoration: none; color: black" href="<c:url value="/messageTest" />">메세지 테스트</a>
			</div>
		</div>
	</c:if>

</section>

<form id="logout" action="<c:url value="/logout" />" method="post">
	<input type="hidden" name="${_csrf.parameterName }"
		value="${_csrf.token }" />
</form>