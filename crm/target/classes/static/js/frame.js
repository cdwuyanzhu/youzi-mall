window.onload=function(){
		//每1秒刷新时间
	  setInterval("NowTime()",1000);
	}
		
	 function NowTime(){
		//获取年月日
		var time=new Date();
		var year=time.getFullYear();var month=time.getMonth();var day=time.getDate();
		var week=time.getDay();//星期
		var arr=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
		//获取时分秒
		var h=time.getHours();var m=time.getMinutes();var s=time.getSeconds();
		//检查是否小于10
		h=check(h);
		m=check(m);
		s=check(s);
		document.getElementById("ymdw").innerHTML=year+"年"+month+"月"+day+"日   "+arr[week];
		document.getElementById("h").innerHTML=h;
		document.getElementById("m").innerHTML=m;
		document.getElementById("s").innerHTML=s;
	}
	function check(i){
		var num;
		i<10?num="0"+i:num=i;
		return num;
	}

// 侧边伸缩
layui.use('element', function(){
		    var element = layui.element;
		
		  });
		  var isShow = true; //定义一个标志位
	$('.kit-side-fold').click(function(){
	//选择出所有的span，并判断是不是hidden
	$('.layui-nav-item span').each(function(){
	  if($(this).is(':hidden')){
		$(this).show();
	  }else{
		$(this).hide();
	  }
	});
	//判断isshow的状态
	if(isShow){
	  $('.layui-side.layui-bg-black').width(50); //设置宽度
	  $('.kit-side-fold i').css('margin-right', '70%'); //修改图标的位置
	  //将footer和body的宽度修改
	  $('.layui-body').css('left', 50+'px');
	  $('.layui-footer').css('left', 50+'px');
	  //将二级导航栏隐藏
	  $('dd span').each(function(){
		$(this).hide();
	  });
	  //修改标志位
	  isShow =false;
	}else{
	  $('.layui-side.layui-bg-black').width(200);
	  $('.kit-side-fold i').css('margin-right', '10%');
	  $('.layui-body').css('left', 200+'px');
	  $('.layui-footer').css('left', 200+'px');
	  $('dd span').each(function(){
		$(this).show();
	  });
	  isShow =true;
	}
	});

$("#test-n1").click(function(){
	console.log("日历")
	layui.use('laydate', function(){
			var laydate = layui.laydate;
			//常规用法
			  laydate.render({
			    elem: '#test-n1',
				// eventElem:"#test-n1",
				trigger: 'click'
			  });
		})
})


//控制全屏
function enterfullscreen() { //进入全屏
//   $("#fullscreen").html("退出全屏");
  var docElm = document.documentElement;
  //W3C
  if(docElm.requestFullscreen) {
    docElm.requestFullscreen();
  }
  //FireFox
  else if(docElm.mozRequestFullScreen) {
    docElm.mozRequestFullScreen();
  }
  //Chrome等
  else if(docElm.webkitRequestFullScreen) {
    docElm.webkitRequestFullScreen();
  }
  //IE11
  else if(elem.msRequestFullscreen) {
    elem.msRequestFullscreen();
  }
}
 
function exitfullscreen() { //退出全屏
//   $("#fullscreen").html("切换全屏");
  if(document.exitFullscreen) {
    document.exitFullscreen();
  } else if(document.mozCancelFullScreen) {
    document.mozCancelFullScreen();
  } else if(document.webkitCancelFullScreen) {
    document.webkitCancelFullScreen();
  } else if(document.msExitFullscreen) {
    document.msExitFullscreen();
  }
}
 
var a = 0;
$('#fullscreen').on('click', function() {
  a++;
  a % 2 == 1 ? enterfullscreen() : exitfullscreen();
})