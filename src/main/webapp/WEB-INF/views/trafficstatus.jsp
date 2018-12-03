<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript">
	var jqueryObj = $;
	var sectionId = ${id}
	
	var auto_refresh = setInterval(function testajax() {
		jqueryObj.ajax({
			type : 'GET',
			url : '/STLC/ajaxtrafficstatus.do/' + sectionId,
			dataType : "json",
			success : function(resData) {
				createImages(resData);
				//alert('ajax 통신성공');
			},
			error : function() {
				//alert('ajax 통신에러');
			}
		});
	}, 1000); // 새로고침 시간 1000ms

	function createImages(resData) {
		let $trafficremaintime = $("#trafficremaintime");
		let $traffictotaltime = $("#traffictotaltime");
		let trafficDatas = resData.items;

		if (trafficDatas[0] != undefined) {
			$trafficremaintime.text(trafficDatas[0].remaintime +" / "+ trafficDatas[0].totaltime + "초");
		}
		
		let i;
		for (i = 0; i < 4; i++) {
			let $timeLabel = $("#timeLabel" + i);
			let $dirLabel = $("#dirLabel" + i);
			let $trafficlight = $("#trafficlight" + i);
			let $trafficimage = $("#trafficimage" + i);

			let data = trafficDatas[i + 1];

			if (data != undefined) {
				$timeLabel.text(data.timeLabel);
				$dirLabel.text(data.dirLabel);
				$trafficlight.attr('src', data.light);
				$trafficimage.attr('src', data.imgPath);
			} else {
				$trafficlight.attr('src', "/STLC/resources/images/light-default.png");
				$trafficimage.attr('src', "/STLC/resources/images/loading.gif");
			}
		}
	}

	var followCursor = (function() {
		var trafficimg = document.createElement('img');
		trafficimg.style.position = 'fixed';
		trafficimg.style.zIndex = '10';

		return {
			init : function(img) {
				trafficimg.src = img.src;

				img.parentElement.appendChild(trafficimg);

				if (trafficimg.clientWidth <= img.clientWidth)
					img.parentElement.removeChild(trafficimg);
			},

			run : function(event) {
				if (trafficimg.clientWidth > window.innerWidth) {
					var mul = trafficimg.clientWidth / window.innerWidth;
					trafficimg.clientWidth = window.innerWidth;
					trafficimg.clientHeight = window.innerHeight * mul;
				}

				var left = event.x - trafficimg.clientWidth / 2;
				var rDiff = event.x + trafficimg.clientWidth / 2 - window.innerWidth;
				if (left < 0)
					left = 0;
				else if (rDiff > 0)
					left -= rDiff;

				var top;
				if (window.innerHeight < event.y + trafficimg.clientHeight)
					top = event.y - 20 - trafficimg.clientHeight;
				else
					top = event.y + 20;

				trafficimg.style.left = left + 'px';
				trafficimg.style.top = top + 'px';
			},

			stop : function(img) {
				img.parentElement.removeChild(trafficimg);
			}
		};
	}());

	function showBigImage(img) {
		followCursor.init(img);
	}

	function moveBigImage(event) {
		followCursor.run(event);
	}

	function hideBigImage(img) {
		followCursor.stop(img);
	}
</script>
<div id="t" class="bg"
	style="text-align: center; font-size: 18px;">

	<div class="row">
		<div class="col-md-1"></div>
	
		<!-- ---------------------------------------west------------------------------------ -->
		<div class="col-md-3" style="height: 100%; margin: auto">
			<div class="traffic">
				<img id="trafficlight1" class="container-fluid" src="<c:url value="/resources/images/light-default.png" />">
				<img id="trafficimage1" class="container-fluid traffic-image" src="<c:url value="/resources/images/loading.gif" />">
				<div class="traffic-label">
					<div style="border: 1px solid red; border-radius: 5px">
						<div id=timeLabel1>WEST</div>
						<div id=dirLabel1>info</div>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-3">
			<!-- ---------------------------------------north------------------------------------ -->
			<div class="traffic">
				<img id="trafficlight3" class="container-fluid" src="<c:url value="/resources/images/light-default.png" />">
				<img id="trafficimage3" class="container-fluid traffic-image" src="<c:url value="/resources/images/loading.gif" />">
				<div class="traffic-label">
					<div style="border: 1px solid red; border-radius: 5px">
						<div id=timeLabel3>NORTH</div>
						<div id=dirLabel3>info</div>
					</div>
				</div>
			</div>

			<!-- ---------------------------------------remain time------------------------------------ -->
			
			<div class="container-fluid" style="font-family: 'Malgun Gothic'; font-size: 18px; text-align: center; border-radius: 25px; background-color: black; color: white; padding: 5px">
				<div><b>남은 시간</b></div>
				<div id="trafficremaintime">0 / 20 <b>초</b></div>
			</div>

			<!-- ---------------------------------------south------------------------------------ -->
			<div class="traffic">
				<img id="trafficlight2" class="container-fluid" src="<c:url value="/resources/images/light-default.png" />">
				<img id="trafficimage2" class="container-fluid traffic-image" src="<c:url value="/resources/images/loading.gif" />">
				<div class="traffic-label">
					<div style="border: 1px solid red; border-radius: 5px">
						<div id=timeLabel2>SOUTH</div>
						<div id=dirLabel2>info</div>
					</div>
				</div>
			</div>
		</div>

		<!-- ----------------------------------east------------------------------------- -->
		<div class="col-md-3" style="height: 100%; margin: auto">
			<div class="traffic">
				<img id="trafficlight0" class="container-fluid" src="<c:url value="/resources/images/light-default.png" />">
				<img id="trafficimage0" class="container-fluid traffic-image" src="<c:url value="/resources/images/loading.gif" />">
				<div class="traffic-label">
					<div style="border: 1px solid red; border-radius: 5px">
						<div id=timeLabel0>EAST</div>
						<div id=dirLabel0>info</div>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-1"></div>
		<!-- -------------------------------------------------------------------------------- -->
	</div>
</div>