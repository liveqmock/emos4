/**
* 小星星打分JS对象
*
* @author tianle
* @since 2010-12-14	
*/
var Star = function(id_name,star_length,star_text){
	this.PRECISION = 1.0;/** 精度 */
	this.SCORE_STARS = 5;/** 显示分数时只有5个星星 */
	this.SCORES = 10;/** 显示分数时只有5个星星 */
	this.id_name = id_name;
	this.star_length = star_length;
	this.stars = new Array(star_length);
	this.init();
	this.lock = false;
	this.star_index = 1;
	this.star_text = document.getElementById(star_text);
}

Star.prototype.Star = Star;

Star.prototype.init = function(){
	for(var i = 0 ; i < this.stars.length ; i++){
		this.stars[i] = document.getElementById(this.id_name+"_"+(i+1));
	}
	this.stars[0].className = "star1";
}

/**
* 预打分，鼠标左右移动
* 很差，差，一般，还行，推荐，精品
*
*/
Star.prototype.beginMark = function(id){
	if(!this.lock){
		var index = id.split("_")[1];
		for(var i = 0 ; i < index ; i++){
			var starBright = this.stars[ i ];
			starBright.className = "star1";//鼠标左侧的星星亮
		}
		for(var i = index ; i <  this.stars.length ; i++){
			var starGray = this.stars[ i ];
			starGray.className = "star2";//鼠标右侧的星星不亮
		}
		this.star_index = index;
		if(index == 1){
			this.star_text.innerText = index+"分"+" 很差";
		}else if(index == 2){
			this.star_text.innerText = index+"分"+ " 差";
		}else if(index == 3){
			this.star_text.innerText = index+"分"+" 一般";
		}else if(index == 4){
			this.star_text.innerText = index+"分"+" 推荐";
		}else if(index == 5){
			this.star_text.innerText = index+"分"+" 精品";
		}
	}
}

/**
* 打分，并且返回得分
*
*/
Star.prototype.mark = function(f){
	this.lock = true;//打过分之后就不能再打分了
	document.getElementById('starnum').value = this.star_index * this.PRECISION;
	this.star_text.innerText= this.star_index * this.PRECISION+"分 感谢您的评价！";
	return this.star_index * this.PRECISION;
}

/**
* 显示得分小星星,一共5颗
*
*/
Star.prototype.score = function(score,id){
	var score_per_star = this.SCORES / this.SCORE_STARS;//每个星的分数
	var num = score / score_per_star;//应该是几个星*.**
	var num_zs = Math.floor(num);//取最小整数
	var num_xs = num - num_zs;//小数
	var i = 0;
	
	if( 0 <= num_xs && num_xs <= 0.25 ){
		i = 0 ;
	}else if( 0.25 < num_xs && num_xs <= 0.85 ){
		i = 0.5;
	}else{
		i = 1;
	}
	var star_num = num_zs + 1;
	
	for(var k = 1 ; k <= num_zs ; k++){
		var star = document.getElementById(id+"_"+k);
		star.className = "star1_";//星星亮
	}
	var star = document.getElementById(id+"_"+star_num);
	if(i == 0){
		star.className = "star2_";//星星不亮
	}
	if(i == 0.5){
		star.className = "star3_";//星星半亮
	}
	if(i == 1){
		star.className = "star1_";//星星亮
	}
}
