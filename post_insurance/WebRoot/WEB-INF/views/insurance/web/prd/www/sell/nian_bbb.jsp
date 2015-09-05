<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
<!--
$("#background,#progressBar").hide();
//-->
</script>
	<link rel="stylesheet" href="/css/jquery.mobile-1.3.2.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/jqm-demos.css"  type="text/css"/>
    <link rel="stylesheet" href="/css/products.css"  type="text/css"/>	
	<link rel="stylesheet" href="/css/demo.css"  type="text/css"/>
	
	<script src="/js/jquery-1.11.0.min.js" type="text/javascript"></script>	
	<script src="/js/jquery.mobile-1.3.2.min.js" type="text/javascript"></script>
	<script src="/js/func_all.js" type="text/javascript"></script> 
	<script src="/js/hj.js" type="text/javascript"></script>	
    <script language="javascript" type="text/javascript">

			function computeGuarantee_bbb(){
				//$("#customerinfoshow").text("success:"+customerinfo.join());
				//alert($("#payperiodmain").text());
				 $("#showage_bbb").text(i_age);
				 if(i_liability=="B") 
					{
						$("#show_main_name").text("公共交通责任组合");
					}
					else
					{
						$("#show_main_name").text("自驾航空责任组合");
					}
				 
				//coverages used in table
				
				
				var i_copiesMain=i_premium/1000;
				var i_duration=0;
				var i_multiple=16;
				var i_age_check;

				f_additionalpremium=i_premium_add*1;	
                f_mainpremium=i_premium*1;		
				
				var i_copiesAdditional=f_additionalpremium/10;
				f_additionalcoverage_bbb=i_copiesAdditional*1000;
				$("#aditionalRiskGuarante_bbb").text(formatNum(f_additionalpremium*100))     //意外伤害医疗保险金额
				$("#mainpremium_bbb").text(formatNum(i_premium*1));     //回显保费
				$("#additionalpremium_bbb").text(formatNum(i_premium_add*1));     //回显附加险保费
				
				 $("#totalpremium").text(formatNum((f_mainpremium+f_additionalpremium)*1));         //保费合计
				 
				if(i_years==1){
					if(i_liability=="B") 
					{
						f_additionalcoverage=arr_bbb_bus_1y[i_age]*i_copiesMain;
					}
					else
					{
						f_additionalcoverage=arr_bbb_car_1y[i_age]*i_copiesMain;
					}
								
					$("#payperiodmain_bbb").text("一次性交费");     //缴费期间
					$("#payperiodaddtional_bbb").text("");          //附加险缴费期间
					i_duration=5;					               //保险期间
					f_max_premium=i_premium*1;                     //满期保费(最大)
					i_age_check=i_age*1+5;                         //保险责任期间满看是否超过18岁
				}
				else{
					if(i_years==3)
					{
						if(i_liability=="B") 
						{
							f_additionalcoverage=arr_bbb_bus_3y[i_age]*i_copiesMain;
						}
						else
						{
							f_additionalcoverage=arr_bbb_car_3y[i_age]*i_copiesMain;
						}
						
						$("#payperiodmain_bbb").text(i_years+"年");     //缴费期间
						$("#payperiodaddtional_bbb").text(i_years+"年");     //缴费期间
						i_duration=6;	                            //保险期间
						f_max_premium=i_premium*3;                     //满期保费(最大)
						i_age_check=i_age*1+6;                         //保险责任期间满看是否超过18岁
					}
					else
					{
						if(i_years==5)
						{
							if(i_liability=="B") 
							{
								f_additionalcoverage=arr_bbb_bus_5y[i_age]*i_copiesMain;
							}
							else
							{
								f_additionalcoverage=arr_bbb_car_5y[i_age]*i_copiesMain;
							}
							
							$("#payperiodmain_bbb").text(i_years+"年");     //缴费期间
							$("#payperiodaddtional_bbb").text(i_years+"年");     //缴费期间
							
							i_duration=10;	                              //保险期间
							f_max_premium=i_premium*5;                     //满期保费(最大)
							i_age_check=i_age*1+10;                         //保险责任期间满看是否超过18岁
						}
						else
						{
							if(i_liability=="B") 
							{
								f_additionalcoverage=arr_bbb_bus_5ay[i_age]*i_copiesMain;
							}
							else
							{
								f_additionalcoverage=arr_bbb_car_5ay[i_age]*i_copiesMain;
							}
							
							$("#payperiodmain_bbb").text("5年");     //缴费期间
							$("#payperiodaddtional_bbb").text("5年");     //缴费期间
							
							i_duration=30;	                              //保险期间
							f_max_premium=i_premium*5;                     //满期保费(最大)
							i_age_check=i_age*1+30;                         //保险责任期间满看是否超过18岁
						}
					}
				}
				
				 
				
				/* 此处判断也是有错,舍去
				if((f_max_premium>=15000) || (i_premium>=15000))
				{
					f_max_premium=15000;                 //最高240万限额
					i_premium=15000;
				}*/
				
				$("#duration_bbb").text(i_duration+"年");                  //回显保险期间
				$("#duration_bbb_a").text(i_duration+"年");                  //回显保险期间
				$("#disease_bbb").text(formatNum(i_premium*1.05));                            //重疾	
				$("#disease_bbb_b").text(formatNum(i_premium*1.05));                          //重疾								
				$("#f_all_coverage_bbb").text(formatNum(f_additionalcoverage*1));      //回显满期保险金=基本保险金额
				$("#f_all_coverage_bbb_b").text(formatNum(f_additionalcoverage*1));     //回显满期保险金=基本保险金额
				$("#f_all_coverage_bbb_dun").text(formatNum(f_additionalcoverage*1));      //回显满期保险金=基本保险金额
				$("#f_all_coverage_bbb_b_dun").text(formatNum(f_additionalcoverage*1));     //回显满期保险金=基本保险金额
				
				$("#disease_bbb").text(formatNum(i_premium*1.05));                          //重疾_min		
				$("#unexpected_air_bbb").text(formatNum(i_premium*160));                    //意外身故_航空_min

				$("#disease_bbb_max").text(formatNum(f_max_premium*1.05));                          //重疾_max
				$("#disease_bbb_max_b").text(formatNum(f_max_premium*1.05));                          //重疾_max
				$("#disease_bbb_max_dun").text(formatNum(f_max_premium*1.05));                          //重疾_max
				$("#disease_bbb_max_b_dun").text(formatNum(f_max_premium*1.05));                          //重疾_max					
				$("#unexpected_air_bbb_max").text(formatNum(f_max_premium*160));                    //意外身故_航空_max
				$("#unexpected_air_bbb_max_dun").text(formatNum(f_max_premium*160));                    //意外身故_航空_max
				
				$("#unexpected_bbb").text(formatNum(i_premium*16));                       //意外身故_min
				$("#unexpected_bbb_max").text(formatNum(f_max_premium*16));                //意外身故_max
				$("#unexpected_bbb_max_dun").text(formatNum(f_max_premium*16));                //意外身故_max
				 
				$("#unexpected_bbb_b").text(formatNum(i_premium*16));                       //意外身故_min
				$("#unexpected_bbb_max_b").text(formatNum(f_max_premium*16));                //意外身故_max
				$("#unexpected_bbb_max_b_dun").text(formatNum(f_max_premium*16));                //意外身故_max
				$("#unexpected_bbb_max_dun").text(formatNum(f_max_premium*16));                //意外身故_max
			
				$("#age_bbb_min1").text(formatNum(i_premium*1.05));                          //18岁以下按照已交保费1.05赔付	
				$("#age_bbb_min2").text(formatNum(i_premium*1.05));                          //18岁以下按照已交保费1.05赔付	
				$("#age_bbb_min3").text(formatNum(i_premium*1.05));                          //18岁以下按照已交保费1.05赔付	
				$("#age_bbb_min4").text(formatNum(i_premium*1.05));                          //18岁以下按照已交保费1.05赔付	
				$("#age_bbb_min5").text(formatNum(i_premium*1.05));                          //18岁以下按照已交保费1.05赔付	
				$("#age_bbb_max1").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max2").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max3").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max4").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max5").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max1_dun").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max2_dun").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max3_dun").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max4_dun").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max5_dun").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				
				$("#age_bbb_min1_below").text(formatNum(i_premium*1.05));                          //18岁以下按照已交保费1.05赔付	
				$("#age_bbb_min2_below").text(formatNum(i_premium*1.05));                          //18岁以下按照已交保费1.05赔付	
				$("#age_bbb_min3_below").text(formatNum(i_premium*1.05));                          //18岁以下按照已交保费1.05赔付	
				$("#age_bbb_min4_below").text(formatNum(i_premium*1.05));                          //18岁以下按照已交保费1.05赔付	
				$("#age_bbb_min5_below").text(formatNum(i_premium*1.05));                          //18岁以下按照已交保费1.05赔付	
				$("#age_bbb_max1_below").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max2_below").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max3_below").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max4_below").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max5_below").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max1_below_dun").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max2_below_dun").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max3_below_dun").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max4_below_dun").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				$("#age_bbb_max5_below_dun").text(formatNum(f_max_premium*1.05));                          //18岁以下按照已交保费1.05赔付
				
				$("#disease_bbb_below").text(formatNum(i_premium*1.05));                            //重疾	
				$("#disease_bbb_b_below").text(formatNum(i_premium*1.05));                          //重疾			
				$("#disease_bbb_max_below").text(formatNum(f_max_premium*1.05));                          //重疾_max
				$("#disease_bbb_max_b_below").text(formatNum(f_max_premium*1.05));                          //重疾_max	
				$("#f_all_coverage_bbb_below").text(formatNum(f_additionalcoverage*1));      //回显满期保险金=基本保险金额
				$("#f_all_coverage_bbb_b_below").text(formatNum(f_additionalcoverage*1));     //回显满期保险金=基本保险金额
				
				$("#disease_bbb_below_dun").text(formatNum(i_premium*1.05));                            //重疾	
				$("#disease_bbb_b_below_dun").text(formatNum(i_premium*1.05));                          //重疾			
				$("#disease_bbb_max_below_dun").text(formatNum(f_max_premium*1.05));                          //重疾_max
				$("#disease_bbb_max_b_below_dun").text(formatNum(f_max_premium*1.05));                          //重疾_max	
				$("#f_all_coverage_bbb_below_dun").text(formatNum(f_additionalcoverage*1));      //回显满期保险金=基本保险金额
				$("#f_all_coverage_bbb_b_below_dun").text(formatNum(f_additionalcoverage*1));     //回显满期保险金=基本保险金额
				
				if(i_years==1)            //趸交
				{
					if(i_liability=="B") 
					{
						$("#unexpected_car_bbb_max_dun").text(formatNum(f_max_premium*16));                    //意外身故_自驾_max
						$("#unexpected_bus_bbb_max_dun").text(formatNum(f_max_premium*160));                    //意外身故_公共交通_max
						$("#unexpected_multiple_bus_dun").text(i_multiple*10+"倍");                 //公共倍数
						$("#unexpected_multiple_air_dun").text(i_multiple*10+"倍");                 //航空倍数
						$("#unexpected_multiple_car_dun").text(i_multiple*1+"倍");                 //自驾车倍数
						$("#group_bbb").text("公共交通计划");                  //回显保险组合
						if(i_age_check>=18)
						{
							$(".echo_bbb_b").hide();
							$(".echo_bbb_a").hide();
							$(".echo_bbb_a_below").hide();
							$(".echo_bbb_b_below").hide();
							$(".echo_bbb_b_dun").show();
							$(".echo_bbb_a_dun").hide();
							$(".echo_bbb_a_below_dun").hide();
							$(".echo_bbb_b_below_dun").hide();
							if(i_age<18)
							{						
								$(".alert_bbb").show();	
							}
							else
							{
								$(".alert_bbb").hide();
							}
						}
						else
						{
							$(".echo_bbb_a_below").hide();
							$(".echo_bbb_a").hide();
							$(".echo_bbb_b").hide();
							$(".echo_bbb_b_below").hide();
							$(".echo_bbb_a_below_dun").hide();
							$(".echo_bbb_a_dun").hide();
							$(".echo_bbb_b_dun").hide();
							$(".echo_bbb_b_below_dun").show();
						}
					}
					else
					{
						$("#unexpected_car_bbb_dun").text(formatNum(i_premium*160));                    //意外身故_自驾_min
						$("#unexpected_car_bbb_max_dun").text(formatNum(f_max_premium*160));                    //意外身故_自驾_max
						$("#unexpected_bus_bbb_dun").text(formatNum(i_premium*16));                    //意外身故_公共交通_min
						$("#unexpected_bus_bbb_max_dun").text(formatNum(f_max_premium*16));                    //意外身故_公共交通_max
						$("#unexpected_multiple_bus_dun").text(i_multiple+"倍");                 //公共倍数
						$("#unexpected_multiple_air_dun").text(i_multiple*10+"倍");                 //航空倍数
						$("#unexpected_multiple_car_dun").text(i_multiple*10+"倍");                 //自驾车倍数
						$("#group_bbb").text("自驾航空计划");                  //回显保险组合
						if(i_age_check>=18)
						{
							$(".echo_bbb_a").hide();
							$(".echo_bbb_b").hide();
							$(".echo_bbb_b_below").hide();
							$(".echo_bbb_a_below").hide();
							$(".echo_bbb_a_dun").show();
							$(".echo_bbb_b_dun").hide();
							$(".echo_bbb_b_below_dun").hide();
							$(".echo_bbb_a_below_dun").hide();
							if(i_age<18)
							{
								$(".alert_bbb").show();
							}
							else
							{
								$(".alert_bbb").hide();
							}
						}
						else
						{
							$(".echo_bbb_a_below").hide();
							$(".echo_bbb_a").hide();
							$(".echo_bbb_b").hide();
							$(".echo_bbb_b_below").hide();
							$(".echo_bbb_a_below_dun").show();
							$(".echo_bbb_a_dun").hide();
							$(".echo_bbb_b_dun").hide();
							$(".echo_bbb_b_below_dun").hide();
						}
					}
				}
				else               
				{
					if(i_liability=="B") 
					{
						$("#unexpected_car_bbb").text(formatNum(i_premium*16));                    //意外身故_自驾_min
						$("#unexpected_car_bbb_max").text(formatNum(f_max_premium*16));                    //意外身故_自驾_max
						$("#unexpected_bus_bbb").text(formatNum(i_premium*160));                    //意外身故_公共交通_min
						$("#unexpected_bus_bbb_max").text(formatNum(f_max_premium*160));                    //意外身故_公共交通_max
						$("#unexpected_multiple_bus").text(i_multiple*10+"倍");                 //公共倍数
						$("#unexpected_multiple_air").text(i_multiple*10+"倍");                 //航空倍数
						$("#unexpected_multiple_car").text(i_multiple*1+"倍");                 //自驾车倍数
						$("#group_bbb").text("公共交通计划");                  //回显保险组合
						if(i_age_check>=18)
						{
							$(".echo_bbb_b").show();
							$(".echo_bbb_a").hide();
							$(".echo_bbb_a_below").hide();
							$(".echo_bbb_b_below").hide();
							$(".echo_bbb_b_dun").hide();
							$(".echo_bbb_a_dun").hide();
							$(".echo_bbb_a_below_dun").hide();
							$(".echo_bbb_b_below_dun").hide();
							if(i_age<18)
							{						
								$(".alert_bbb").show();	
							}
							else
							{
								$(".alert_bbb").hide();
							}
						}
						else
						{
							$(".echo_bbb_a_below").hide();
							$(".echo_bbb_a").hide();
							$(".echo_bbb_b").hide();
							$(".echo_bbb_b_below").show();
							$(".echo_bbb_a_below_dun").hide();
							$(".echo_bbb_a_dun").hide();
							$(".echo_bbb_b_dun").hide();
							$(".echo_bbb_b_below_dun").hide();
						}
					}
					else
					{
						$("#unexpected_car_bbb").text(formatNum(i_premium*160));                    //意外身故_自驾_min
						$("#unexpected_car_bbb_max").text(formatNum(f_max_premium*160));                    //意外身故_自驾_max
						$("#unexpected_bus_bbb").text(formatNum(i_premium*16));                    //意外身故_公共交通_min
						$("#unexpected_bus_bbb_max").text(formatNum(f_max_premium*16));                    //意外身故_公共交通_max
						$("#unexpected_multiple_bus").text(i_multiple+"倍");                 //公共倍数
						$("#unexpected_multiple_air").text(i_multiple*10+"倍");                 //航空倍数
						$("#unexpected_multiple_car").text(i_multiple*10+"倍");                 //自驾车倍数
						$("#group_bbb").text("自驾航空计划");                  //回显保险组合
						if(i_age_check>=18)
						{
							$(".echo_bbb_a").show();
							$(".echo_bbb_b").hide();
							$(".echo_bbb_b_below").hide();
							$(".echo_bbb_a_below").hide();
							$(".echo_bbb_a_dun").hide();
							$(".echo_bbb_b_dun").hide();
							$(".echo_bbb_b_below_dun").hide();
							$(".echo_bbb_a_below_dun").hide();
							if(i_age<18)
							{
								$(".alert_bbb").show();
							}
							else
							{
								$(".alert_bbb").hide();
							}
						}
						else
						{
							$(".echo_bbb_a_below").show();
							$(".echo_bbb_a").hide();
							$(".echo_bbb_b").hide();
							$(".echo_bbb_b_below").hide();
							$(".echo_bbb_a_below_dun").hide();
							$(".echo_bbb_a_dun").hide();
							$(".echo_bbb_b_dun").hide();
							$(".echo_bbb_b_below_dun").hide();
						}
					}
				}
			}	 /*年年好百倍保*/

			 

			
		 
		$(document).on("pageinit", function() {	

			/*年年好百倍保  info  */ 
			  $("#fiveYear_bbb_a").click(function(){	
					$("#explain_bbb").text("被保险人年龄（0至40岁）：");   
					$('#age_bbb').attr('max','40');
					$('#age_bbb').val(25);
			}); 
			$("#oneYear_bbb").click(function(){	
					$("#explain_bbb").text("被保险人年龄（0至60岁）：");   
					$('#age_bbb').attr('max','60');
					$('#age_bbb').val(25);
			}); 
			$("#threeYear_bbb").click(function(){	
					$("#explain_bbb").text("被保险人年龄（0至60岁）：");   
					$('#age_bbb').attr('max','60');
					$('#age_bbb').val(25);
			}); 
			$("#fiveYear_bbb").click(function(){	
					$("#explain_bbb").text("被保险人年龄（0至60岁）：");   
					$('#age_bbb').attr('max','60');
					$('#age_bbb').val(25);
			});
		
			$("#getguarantee_bbb").click(function(){
				//if premiuOK,renew & show page nianbguarantee
				 i_money_check=$("#premium_input_bbb").val();                    //强行赋值,检查函数使用 
				 i_money_check_bbb=$("#premium_input_bbb_a").val();                    //强行赋值,检查函数使用 20150730add
				if(premiumOK_bbb()&&premiumOK_bbb2()){
					i_age=$("#age_bbb").val();
					//i_sex=$("#male").is(':checked') ? 1:0;
					i_years=$('input:radio[name="years_bbb"]:checked').val();
					i_liability=$('input:radio[name="liability_bbb"]:checked').val();
					i_premium=$("#premium_input_bbb").val(); //old var  is customerinfo[3]
					i_premium_add=$("#premium_input_bbb_a").val();                    //附加险 20150730 add
				
					computeGuarantee_bbb();
					$.mobile.changePage("#bbb_guarantee");
					 
				}
			});
		});  
	</script>
</head>
<body>
    
<!-- page nian_bbb_info   -->
<div data-role="page"  id="nian_bbb_info" class="info">
    <div data-role="content">
        <div class="customerinfo">
        	
                   <!-- <div class="customertext">被保险人年龄（0至70岁）：</div>
                    <div class="divage"><input id="age_bbb" type="range"  value="25" min="0" max="70" /></div><br/><hr/><br/>--->
                    <div class="customertext">交费及保障期：</div>
                    <div class="ui-field-contain">
  						<fieldset data-role="controlgroup" data-type="horizontal">	
                                <input type="radio" name="years_bbb" id="oneYear_bbb" value="1" onclick="oneYear_bbb" />
                                <label for="oneYear_bbb" data-mini="true">趸交5年期</label>
                                <input type="radio" name="years_bbb" id="threeYear_bbb" value="3" onclick="threeYear_bbb" />
                                <label for="threeYear_bbb" data-mini="true">3年交6年期</label>
                                <input type="radio" name="years_bbb" id="fiveYear_bbb" value="5"  onclick="fiveYear_bbb" />
                                <label for="fiveYear_bbb" data-mini="true">5年交10年期</label>
                                <input type="radio" name="years_bbb" id="fiveYear_bbb_a" value="5a"  checked="checked"  onclick="fiveYear_bbb_a"/>
                                <label for="fiveYear_bbb_a" data-mini="true"> 5年交30年期</label>           
                    	</fieldset>
                    </div>	
                    <hr/><br/>
					<div class="customertext">保险组合</div>
                    
                        <div class="ui-field-contain">
	  						<fieldset data-role="controlgroup">	
	                            <input type="radio" name="liability_bbb" id="liability_A" value="A"  checked="checked" />
                               	<label for="liability_A" data-mini="true">A-自驾航空计划</label>
                               	<input type="radio" name="liability_bbb" id="liability_B" value="B"/>
                               	<label for="liability_B" data-mini="true">B-公共交通计划  </label>                                	
	                    	</fieldset>
	                    </div>
                    <hr/><br/>
					<span  class="divage_bbb_40"><div class="customertext" id="explain_bbb" >被保险人年龄（0至40岁）：</div> 
						<input id="age_bbb" type="range"  value="25" min="0" max="40" /></span><br/><hr/><br/>
                    <div class="customertext">年交费合计（要求为500的整数倍，2份起售，单位：元）：</div>
                    <input class="premium_input" id="premium_input_bbb" type="number" data-mini="true" value="1000" min="1000" step="500"/>
                    <span class="premiumalert">提示:本款保险产品每份保费500元,最低2份起售,<span span id="remind_bbb"></span>大于1000且为500整倍数的数值!(工具仅做自动核保范围内的演示)</span><br/><hr/>
					<!--20150730  add   附加险-->
					<div class="customertext">附加险年交费合计（每1000元保险金额对应10元保险费，要求输入为10的整数倍，单位：元）：</div>
                    <input class="premium_input1" id="premium_input_bbb_a" type="number" data-mini="true" value="0" min="0" step="10"/>
					<span class="premiumalert_ddb"><span span id="remind_bbb2"></span> (工具仅做自动核保范围内的演示)</span><br/><hr/>
		</div> 
        <div class="ui-block-a getguaranteediv"><div  data-role="button" id="getguarantee_bbb">计算保险利益</div></div><br/><br/>
    </div>
    <div class="oneblankdiv"></div>
    <div class="blankdiv"></div>
    <div data-role="footer" class="footer">
    	<div class="footertext">中邮保险销售辅助工具V3.0.2</div>
    </div>
</div> <!--End of page nian_bbb_info -->



<!---nian_bbb guarantee-->
<div data-role="page" id="bbb_guarantee"  class="info">
    <div data-role="content">
	 <div style=" position: absolute;line-height:1em;right:0.5%;top:0.5em;"><a   data-add-back-btn="true" style="font-size:1em;"   data-rel="back" data-icon="back" data-role="button" class="ui-btn-right" >返回</a></div><br/>
        <div><span class="title">产品亮点：</span><span  class="title">1</span>份投入  &nbsp;&nbsp;   <span  class="title">3</span>重保障  &nbsp;&nbsp;   <span  class="title">160</span>倍关爱 
        </div><br/>
        <div><span class="title">基本信息:</span>年年好百倍保；被保险人年龄<span id="showage_bbb" class="parameter"> </span>岁        
        	<div class="maininfotable">	        	
	    		 <table data-role="table"  class="ui-body-d ui-shadow table-stripe ui-responsive"  >
			         <thead>
			           <tr class="ui-bar-b"><th>险种名称</th><th>交费期间</th><th>保险期间</th><th>所交保费</th>
			           </tr>
			         </thead>
			         <tbody>          
			           <tr>	             
					    <td><span class="parameter">百倍保<span id="show_main_name" class="parameter"> </span></span></td>
			             <td><span id="payperiodmain_bbb" class="parameter"> </span></td>
			             <td><span id="duration_bbb" class="parameter"> </span></td>
						 <td><span id="mainpremium_bbb" class="parameter"> </span></td>
			           </tr>	   
						<tr>	             
					    <td><span class="parameter">中邮附加意外伤害医疗保险</span></td>
			             <td><span id="payperiodaddtional_bbb" class="parameter"> </span></td>
			             <td><span id="duration_bbb_a" class="parameter"> </span></td>
						 <td><span id="additionalpremium_bbb" class="parameter"> </span></td>
			           </tr>	
					   <tr>
			           	<td><span class="parameter">年交费合计：</span></td>
			           	<td>&nbsp;</td>
			           	<td>&nbsp;</td>
			           	<td><span id="totalpremium" class="parameter"></span></td>
			           </tr>
			         </tbody>
		       </table> 
	    	</div>
    	</div>	
			<div>
        	 <div class="title">保险责任：</div>
			 <span  class="echo_bbb_a">
			 <div class="title2">(一)自驾车交通意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起因驾驶或乘坐自驾车（见合同条款）期间遭受意外伤害事故导致身故或全残，按照已交保费的<span  class="parameter" id="unexpected_multiple_car"></span>给付"自驾车交通意外身故、全残保险金"，最低给付<span class="coverage" id="unexpected_car_bbb"></span>元，最高给付<span class="coverage" id="unexpected_car_bbb_max"></span>元，本合同效力终止。</div>
			 <div class="alert_bbb">&nbsp;&nbsp;&nbsp;若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“自驾车意外身故、全残保险金”,最低给付<span class="coverage" id="age_bbb_min1"></span>元，最高给付<span class="coverage" id="age_bbb_max1"></span>元,本合同效力终止。</div> 
			 <div class="title2">(二)航空交通意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起搭乘指定公共交通工具（见合同条款）期间遭受意外伤害事故导致身故或全残，按照已交保费的<span  class="parameter" id="unexpected_multiple_air"></span>给付"航空交通意外身故、全残保险金"，最低给付<span class="coverage" id="unexpected_air_bbb"></span>元，最高给付<span class="coverage" id="unexpected_air_bbb_max"></span>元，本合同效力终止。</div> 
			 <div class="alert_bbb">&nbsp;&nbsp;&nbsp;若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“航空交通意外身故、全残保险金”,最低给付<span class="coverage" id="age_bbb_min2"></span>元，最高给付<span class="coverage" id="age_bbb_max2"></span>元,本合同效力终止。</div>
			 <div class="title2">(三)意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起因意外导致身故或全残，按照已交保费的<span  class="parameter">16倍</span>给付"意外身故、全残保险金"，最低给付<span class="coverage" id="unexpected_bbb"></span>元，最高给付<span class="coverage" id="unexpected_bbb_max"></span>元，本合同效力终止。</div>   
			 <div class="alert_bbb">&nbsp;&nbsp;&nbsp;若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“意外身故、全残保险金”,最低给付<span class="coverage" id="age_bbb_min3"></span>元，最高给付<span class="coverage" id="age_bbb_max3"></span>元,本合同效力终止。</div>
			 <div class="title2">(四)身故、全残保险金金</div>
             <div class="coveragetext">被保险人于合同生效之日起因非意外导致身故或全残，按已交保费的105%给付"身故、全残保险金"，最低给付<span class="coverage" id="disease_bbb"></span>元，最高给付<span class="coverage" id="disease_bbb_max"></span>元，合同效力终止。</div>
			  <div class="title2">(五)意外伤害医疗保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起遭受意外伤害事故，按条款给付保险责任期间遭受意外伤害而发生的合理且必要的医疗费用<span class="coverage" id="aditionalRiskGuarante_bbb">0</span>元，本合同效力终止。</div> 
             <div class="title2">(六)满期保险金</div>
             <div class="coveragetext">保险合同期满，领取满期金<span class="coverage" id="f_all_coverage_bbb"></span>元，本合同效力终止。</div> <br/>
			 </span>
			 <span  class="echo_bbb_a_below">
			 <div class="title2">(一)自驾车交通意外身故、全残保险金</div>
             <div class="coveragetext">若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“自驾车意外身故、全残保险金”,最低给付<span class="coverage" id="age_bbb_min1_below"></span>元，最高给付<span class="coverage" id="age_bbb_max1_below"></span>元,本合同效力终止。</div>
			 <div class="title2">(二)航空交通意外身故、全残保险金</div>
             <div class="coveragetext">若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“航空交通意外身故、全残保险金”,最低给付<span class="coverage" id="age_bbb_min2_below"></span>元，最高给付<span class="coverage" id="age_bbb_max2_below"></span>元,本合同效力终止。</div> 
			 <div class="title2">(三)意外身故、全残保险金</div>
             <div class="coveragetext">若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“意外身故、全残保险金”,最低给付<span class="coverage" id="age_bbb_min3_below"></span>元，最高给付<span class="coverage" id="age_bbb_max3_below"></span>元,本合同效力终止。</div>   
			 <div class="title2">(四)身故、全残保险金金</div>
             <div class="coveragetext">被保险人于合同生效之日起因非意外导致身故或全残，按已交保费的105%给付"身故、全残保险金"，最低给付<span class="coverage" id="disease_bbb_below_below"></span>元，最高给付<span class="coverage" id="disease_bbb_max_below"></span>元，合同效力终止。</div>
             <div class="title2">(五)意外伤害医疗保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起遭受意外伤害事故，按条款给付保险责任期间遭受意外伤害而发生的合理且必要的医疗费用<span class="coverage" id="aditionalRiskGuarante_bbb">0</span>元，本合同效力终止。</div> 
			 <div class="title2">(六)满期保险金</div>
             <div class="coveragetext">保险合同期满，领取满期金<span class="coverage" id="f_all_coverage_bbb_below"></span>元，本合同效力终止。</div> <br/>
			 </span>
			 <span  class="echo_bbb_b">
			 <div class="title2">(一)公共交通意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起搭乘指定公共交通工具（见合同条款）期间遭受意外伤害事故导致身故或全残，按照已交保费的<span  class="parameter" id="unexpected_multiple_bus"></span>给付"公共交通意外身故、全残保险金"，最低给付<span class="coverage" id="unexpected_bus_bbb"></span>元，最高给付<span class="coverage" id="unexpected_bus_bbb_max"></span>元，本合同效力终止。</div> 
			 <div class="alert_bbb">&nbsp;&nbsp;&nbsp;若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“公共交通意外身故、全残保险金”,最低给付<span class="coverage" id="age_bbb_min4"></span>元，最高给付<span class="coverage" id="age_bbb_max4"></span>元,本合同效力终止。</div>
			 <div class="title2">(二)意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起因意外导致身故或全残，按照已交保费的<span  class="parameter">16倍</span>给付"意外身故、全残保险金"，最低给付<span class="coverage" id="unexpected_bbb_b"></span>元，最高给付<span class="coverage" id="unexpected_bbb_max_b"></span>元，本合同效力终止。</div> 
			 <div class="alert_bbb">&nbsp;&nbsp;&nbsp;若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“意外身故、全残保险金”,最低给付<span class="coverage" id="age_bbb_min5"></span>元，最高给付<span class="coverage" id="age_bbb_max5"></span>元,本合同效力终止。</div>			 
			 <div class="title2">(三)身故、全残保险金金</div>
             <div class="coveragetext">被保险人于合同生效之日起因非意外导致身故或全残，按已交保费的105%给付"身故、全残保险金"，最低给付<span class="coverage" id="disease_bbb_b"></span>元，最高给付<span class="coverage" id="disease_bbb_max_b"></span>元，合同效力终止。</div>
             <div class="title2">(四)意外伤害医疗保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起遭受意外伤害事故，按条款给付保险责任期间遭受意外伤害而发生的合理且必要的医疗费用<span class="coverage" id="aditionalRiskGuarante_bbb">0</span>元，本合同效力终止。</div> 
			 <div class="title2">(五)满期保险金</div>
             <div class="coveragetext">保险合同期满，领取满期金<span class="coverage" id="f_all_coverage_bbb_b"></span>元，本合同效力终止。</div> <br/>
			 </span>
			 <span  class="echo_bbb_b_below">
			 <div class="title2">(一)公共交通意外身故、全残保险金</div>
             <div class="coveragetext">若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“公共交通意外身故、全残保险金”,最低给付<span class="coverage" id="age_bbb_min4_below"></span>元，最高给付<span class="coverage" id="age_bbb_max4_below"></span>元,本合同效力终止。</div> 
			 <div class="title2">(二)意外身故、全残保险金</div>
             <div class="coveragetext">若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“意外身故、全残保险金”,最低给付<span class="coverage" id="age_bbb_min5_below"></span>元，最高给付<span class="coverage" id="age_bbb_max5_below"></span>元,本合同效力终止。</div> 
			  <div class="title2">(三)身故、全残保险金金</div>
             <div class="coveragetext">被保险人于合同生效之日起因非意外导致身故或全残，按已交保费的105%给付"身故、全残保险金"，最低给付<span class="coverage" id="disease_bbb_b_below"></span>元，最高给付<span class="coverage" id="disease_bbb_max_b_below"></span>元，合同效力终止。</div>
             <div class="title2">(四)意外伤害医疗保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起遭受意外伤害事故，按条款给付保险责任期间遭受意外伤害而发生的合理且必要的医疗费用<span class="coverage" id="aditionalRiskGuarante_bbb">0</span>元，本合同效力终止。</div> 
			 <div class="title2">(五)满期保险金</div>
             <div class="coveragetext">保险合同期满，领取满期金<span class="coverage" id="f_all_coverage_bbb_b_below"></span>元，本合同效力终止。</div> <br/>
			 </span>
			 <!---趸交不要最低最高--->
			 <span  class="echo_bbb_a_dun">
			 <div class="title2">(一)自驾车交通意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起因驾驶或乘坐自驾车（见合同条款）期间遭受意外伤害事故导致身故或全残，按照已交保费的<span  class="parameter" id="unexpected_multiple_car_dun"></span>给付"自驾车交通意外身故、全残保险金"<span class="coverage" id="unexpected_car_bbb_max_dun"></span>元，本合同效力终止。</div>
			 <div class="alert_bbb">&nbsp;&nbsp;&nbsp;若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“自驾车意外身故、全残保险金”<span class="coverage" id="age_bbb_max1_dun"></span>元,本合同效力终止。</div> 
			 <div class="title2">(二)航空交通意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起搭乘指定公共交通工具（见合同条款）期间遭受意外伤害事故导致身故或全残，按照已交保费的<span  class="parameter" id="unexpected_multiple_air_dun"></span>给付"航空交通意外身故、全残保险金"<span class="coverage" id="unexpected_air_bbb_max_dun"></span>元，本合同效力终止。</div> 
			 <div class="alert_bbb">&nbsp;&nbsp;&nbsp;若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“航空交通意外身故、全残保险金”<span class="coverage" id="age_bbb_max2_dun"></span>元,本合同效力终止。</div>
			 <div class="title2">(三)意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起因意外导致身故或全残，按照已交保费的<span  class="parameter">16倍</span>给付"意外身故、全残保险金"<span class="coverage" id="unexpected_bbb_max_dun"></span>元，本合同效力终止。</div>   
			 <div class="alert_bbb">&nbsp;&nbsp;&nbsp;若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“意外身故、全残保险金”<span class="coverage" id="age_bbb_max3_dun"></span>元,本合同效力终止。</div>
			 <div class="title2">(四)身故、全残保险金金</div>
             <div class="coveragetext">被保险人于合同生效之日起因非意外导致身故或全残，按已交保费的105%给付"身故、全残保险金"<span class="coverage" id="disease_bbb_max_dun"></span>元，合同效力终止。</div>
            <div class="title2">(五)意外伤害医疗保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起遭受意外伤害事故，按条款给付保险责任期间遭受意外伤害而发生的合理且必要的医疗费用<span class="coverage" id="aditionalRiskGuarante_bbb">0</span>元，本合同效力终止。</div> 
			<div class="title2">(六)满期保险金</div>
             <div class="coveragetext">保险合同期满，领取满期金<span class="coverage" id="f_all_coverage_bbb_dun"></span>元，本合同效力终止。</div> <br/>
			 </span>
			 <span  class="echo_bbb_a_below_dun">
			 <div class="title2">(一)自驾车交通意外身故、全残保险金</div>
             <div class="coveragetext">若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“自驾车意外身故、全残保险金”<span class="coverage" id="age_bbb_max1_below_dun"></span>元,本合同效力终止。</div>
			 <div class="title2">(二)航空交通意外身故、全残保险金</div>
             <div class="coveragetext">若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“航空交通意外身故、全残保险金”<span class="coverage" id="age_bbb_max2_below_dun"></span>元,本合同效力终止。</div> 
			 <div class="title2">(三)意外身故、全残保险金</div>
             <div class="coveragetext">若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“意外身故、全残保险金”<span class="coverage" id="age_bbb_max3_below_dun"></span>元,本合同效力终止。</div>   
			 <div class="title2">(四)身故、全残保险金金</div>
             <div class="coveragetext">被保险人于合同生效之日起因非意外导致身故或全残，按已交保费的105%给付"身故、全残保险金"<span class="coverage" id="disease_bbb_max_below_dun"></span>元，合同效力终止。</div>
            <div class="title2">(五)意外伤害医疗保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起遭受意外伤害事故，按条款给付保险责任期间遭受意外伤害而发生的合理且必要的医疗费用<span class="coverage" id="aditionalRiskGuarante_bbb">0</span>元，本合同效力终止。</div>
			<div class="title2">(六)满期保险金</div>
             <div class="coveragetext">保险合同期满，领取满期金<span class="coverage" id="f_all_coverage_bbb_below_dun"></span>元，本合同效力终止。</div> <br/>
			 </span>
			 <span  class="echo_bbb_b_dun">
			 <div class="title2">(一)公共交通意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起搭乘指定公共交通工具（见合同条款）期间遭受意外伤害事故导致身故或全残，按照已交保费的<span  class="parameter" id="unexpected_multiple_bus_dun"></span>给付"公共交通意外身故、全残保险金"<span class="coverage" id="unexpected_bus_bbb_max_dun"></span>元，本合同效力终止。</div> 
			 <div class="alert_bbb">&nbsp;&nbsp;&nbsp;若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“公共交通意外身故、全残保险金”<span class="coverage" id="age_bbb_max4_dun"></span>元,本合同效力终止。</div>
			 <div class="title2">(二)意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起因意外导致身故或全残，按已交保费的<span  class="parameter">16倍</span>给付"意外身故、全残保险金"<span class="coverage" id="unexpected_bbb_max_b_dun"></span>元，本合同效力终止。</div> 
			 <div class="alert_bbb">&nbsp;&nbsp;&nbsp;若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“意外身故、全残保险金”<span class="coverage" id="age_bbb_max5_dun"></span>元,本合同效力终止。</div>			 
			 <div class="title2">(三)身故、全残保险金金</div>
             <div class="coveragetext">被保险人于合同生效之日起因非意外导致身故或全残，按已交保费的105%给付"身故、全残保险金"<span class="coverage" id="disease_bbb_max_b_dun"></span>元，合同效力终止。</div>
             <div class="title2">(四)意外伤害医疗保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起遭受意外伤害事故，按条款给付保险责任期间遭受意外伤害而发生的合理且必要的医疗费用<span class="coverage" id="aditionalRiskGuarante_bbb">0</span>元，本合同效力终止。</div> <br/>
			 <div class="title2">(五)满期保险金</div>
             <div class="coveragetext">保险合同期满，领取满期金<span class="coverage" id="f_all_coverage_bbb_b_dun"></span>元，本合同效力终止。</div> 
			 </span>
			 <span  class="echo_bbb_b_below_dun">
			 <div class="title2">(一)公共交通意外身故、全残保险金</div>
             <div class="coveragetext">若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“公共交通意外身故、全残保险金”<span class="coverage" id="age_bbb_max4_below_dun"></span>元,本合同效力终止。</div> 
			 <div class="title2">(二)意外身故、全残保险金</div>
             <div class="coveragetext">若被保险人发生身故或全残未满18 周岁的，按已交保险费的105%给付“意外身故、全残保险金”<span class="coverage" id="age_bbb_max5_below_dun"></span>元,本合同效力终止。</div> 
			  <div class="title2">(三)身故、全残保险金金</div>
             <div class="coveragetext">被保险人于合同生效之日起因非意外导致身故或全残，按已交保费的105%给付"身故、全残保险金"<span class="coverage" id="disease_bbb_max_b_below_dun"></span>元，合同效力终止。</div>
             <div class="title2">(四)意外伤害医疗保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起遭受意外伤害事故，按条款给付保险责任期间遭受意外伤害而发生的合理且必要的医疗费用<span class="coverage" id="aditionalRiskGuarante_bbb">0</span>元，本合同效力终止。</div> <br/>
			 <div class="title2">(五)满期保险金</div>
             <div class="coveragetext">保险合同期满，领取满期金<span class="coverage" id="f_all_coverage_bbb_b_below_dun"></span>元，本合同效力终止。</div> <br/>
			 </span>
			 <!---趸交不要最低最高end-->
			 <div class="title">合同权益：</div>
             <div class="title2">保单借款</div>
            <div class="coveragetext">最高可以借保单现金价值的90%。</div>      <br/>    			
			 <div class="alert">本计划书仅供参考，详细内容以保险合同条款为准。</div><hr/>
			</div>
		</div>
		<br/><br/>
		<div data-role="footer" class="footer">
			<div class="footertext">中邮保险销售辅助工具V3.0.2</div>
		</div>	
 
</div><!-- end of nian_bbb guarantee -->
