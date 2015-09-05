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
	
    <script  type="text/javascript">
	
			function computeGuarantee_fu3(){
				//$("#customerinfoshow").text("success:"+customerinfo.join());
				//alert($("#payperiodmain").text());
				 $("#showage_f3").text(i_age);
				//coverages used in table
				
				f_mainpremium=i_premium*1;
				f_additionalpremium=i_premium_add*1;		
				
				
				var i_copiesMain=i_premium/1000;
				var i_f3=9;
				var i_copiesAdditional=f_additionalpremium/10;
				
				
				f_additionalcoverage_f3=i_copiesAdditional*1000;
				$("#additionalcoverage_f3").text(formatNum(f_additionalcoverage_f3));       //回显附加险保险金额
				$("#aditionalRiskGuarante_f3").text(formatNum(f_additionalpremium*100))     //意外伤害医疗保险金额
				
				if(i_age<=40)
				{
					i_f3=0;				
				}
				else
				{	
					if((i_age>=41)&&(i_age<=50))
					{
						i_f3=1;	
					}
					else
					{
						if((i_age>=51)&&(i_age<=55))
						{
							i_f3=2;	
						}
						else
						{
							if((i_age>=56)&&(i_age<=60))
							{
								i_f3=3;	
							}
							else
							{
								if((i_age>=61)&&(i_age<=65))
								{
									i_f3=4;	
								}
								else
								{
									i_f3=5;	
								}
							}
						}
					}
				}
				
				if(i_years==3){
					f_additionalcoverage=arr_fu3[1][i_f3]*i_copiesMain;
					f_all_coverage=f_additionalcoverage*3;
					$("#coverage_f3").text(formatNum(f_additionalcoverage*1));   //回显基本保额       
					$("#mainpremium_f3").text(formatNum(i_premium*1));     //回显保费
					$("#additionalpremium_f3").text(formatNum(i_premium_add*1));     //回显附加险保费
					$("#payperiodmain_f3").text(i_years+"年");             //回显交费期间
					$("#payperiodaddtional_f3").text(i_years+"年");
				}
				else{
					if(i_years==6)
					{
						f_additionalcoverage=arr_fu3[2][i_f3]*i_copiesMain;
						f_all_coverage=f_additionalcoverage*6;
						$("#coverage_f3").text(formatNum(f_additionalcoverage*1));   //回显基本保额       
						$("#mainpremium_f3").text(formatNum(i_premium*1));     //回显保费
						$("#additionalpremium_f3").text(formatNum(i_premium_add*1));     //回显附加险保费
						$("#payperiodmain_f3").text(i_years+"年");
						$("#payperiodaddtional_f3").text(i_years+"年");
					}
					else
					{
						f_additionalcoverage=arr_fu3[0][i_f3]*i_copiesMain;
						f_all_coverage=f_additionalcoverage*1;
						$("#coverage_f3").text(formatNum(f_additionalcoverage*1));   //回显基本保额       
						$("#mainpremium_f3").text(formatNum(i_premium*1));     //回显保费
						$("#additionalpremium_f3").text(formatNum(i_premium_add*1));     //回显附加险保费
						$("#payperiodmain_f3").text("一次性交费");
						$("#payperiodaddtional_f3").text("");
					}
				}
				
				
                $("#totalpremium").text(formatNum((f_mainpremium+f_additionalpremium)*1));         //保费合计
				
				/*20150727 新产品升级修改
				$("#mainpremium_f3_a").text(formatNum(i_premium*1));     //回显保费         */
				
				if(f_additionalcoverage>=i_premium*1.05)
				{
					$("#mainpremium_f3_a").text(formatNum(f_additionalcoverage*1));  
				}
				else
				{
					$("#mainpremium_f3_a").text(formatNum(i_premium*1.05));  
				}
				
				$("#f_all_coverage_f3").text(formatNum(f_all_coverage*1));     //回显满期保险金
				 

				$("#unexpectedgomin_f3_a").text(formatNum(f_additionalcoverage));   //回显最低金额=三种缴费方式的基本保额*缴费1年
				$("#unexpectedgomin_f3_b").text(formatNum(f_additionalcoverage*2));   //回显最低金额=三种缴费方式的基本保额*缴费1年*2倍
				$("#unexpectedgomin_f3_c").text(formatNum(f_additionalcoverage*3));   //回显最低金额=三种缴费方式的基本保额*缴费1年*3倍
				
				$("#unexpectedgomax_f3_a").text(formatNum(f_all_coverage));   //回显最高金额=三种缴费方式的基本保额*缴费年度*1倍
				$("#unexpectedgomax_f3_b").text(formatNum(f_all_coverage*2));   //回显最高金额=三种缴费方式的基本保额*缴费年度*2倍
				$("#unexpectedgomax_f3_c").text(formatNum(f_all_coverage*3));   //回显最高金额=三种缴费方式的基本保额*缴费年度*3倍
				

			}	 /*fu3*/
			
		 
		$(document).on("pageinit", function() {		 
		
		  /*富富余3  info  */ 
			  $("#0year_f3").click(function(){	
					$("#explain_fu3").text("被保险人年龄（出生满30天至70周岁）：");   
					$('#age_f3').attr('max','70');
					$('#age_f3').val(25).change();
			}); 
			$("#3year_f3").click(function(){	
					$("#explain_fu3").text("被保险人年龄（出生满30天至70周岁）：");   
					$('#age_f3').attr('max','70');
					$('#age_f3').val(25).change();
			}); 
			$("#6year_f3").click(function(){	
					$("#explain_fu3").text("被保险人年龄（出生满30天至50周岁）：");   
					$('#age_f3').attr('max','50');
					$('#age_f3').val(25).change();
			});
		
		
			$("#getguarantee_fu3").click(function(){
				//if premiuOK,renew & show page nianbguarantee
				 i_money_check=$("#premium_input_f3").val();                    //强行赋值,检查函数使用 
				 i_money_check_f3=$("#premium_input_f3_a").val();                    //强行赋值,检查函数使用 
				 i_year_check=$('input:radio[name="years_f3"]:checked').val();
				 
				if(premiumOK_2()&&premiumOK_f3()){
					i_age=$("#age_f3").val();
					//i_sex=$("#male").is(':checked') ? 1:0;
					i_years=$('input:radio[name="years_f3"]:checked').val();
					i_premium=$("#premium_input_f3").val(); //old var  is customerinfo[3]
					i_premium_add=$("#premium_input_f3_a").val();
					computeGuarantee_fu3();
					$.mobile.changePage("#fu3guarantee" );
					 
				}
			});
			
			$(".nav ul li").hover(
				function(){
					jQuery(this).children(".drop_dwon").slideDown(200);
					jQuery(this).children(".nav ul li a").attr("id","currlayout")
				},
				function(){
					jQuery(this).children(".drop_dwon").slideUp(100);
					jQuery(this).children(".nav ul li a").attr("id","")
				}
			);
		});  
	</script>
</head>
<body>
    
<!-- page Fu3info   -->
<div data-role="page"  id="fu3info" class="info">
    <div data-role="content">
		 
        <div class="customerinfo">
        	 <div class="customertext">基本信息:富富余3号两全保险(分红型)</div>
                   
                    <div class="customertext">交费及保障期：</div>
                    <div class="ui-field-contain">
  						<fieldset data-role="controlgroup" data-type="horizontal">	
                                <input type="radio" name="years_f3" id="0year_f3" value="0"  />
                                <label for="0year_f3" data-mini="true" >趸交</label>
                                <input type="radio" name="years_f3" id="3year_f3" value="3"  />
                                <label for="3year_f3" data-mini="true">3年</label>                        
                                <input type="radio" name="years_f3" id="6year_f3" value="6" checked="checked"  />
                                <label for="6year_f3" data-mini="true">6年 </label>			           
                    	</fieldset>
                    </div>	
					<span  class="divage_bbb_40"><div class="customertext" id="explain_fu3" >被保险人年龄（0至50岁）：</div> 
				    <input id="age_f3" type="range"  value="25" min="0" max="50" /></span><br/><hr/><br/>
					<!--20150727
					<div class="customertext">被保险人年龄（0至70岁）：</div>
                    <div class="divage"><input id="age_f3" type="range"  value="25" min="0" max="70" /></div><br/><hr/><br/>
					-->
                    <hr/><br/>
                    <div class="customertext">年交费合计（要求为500的整数倍，2份起售，单位：元）：</div>
                    <input class="premium_input" id="premium_input_f3" type="number" data-mini="true" value="1000" min="1000" step="500"/>
                    <span class="premiumalert">本保险产品每份保费500元,最低2份起售,请输入大于1000且为500整倍数的数值！</span><br/><hr/>
					<!--20150730  add   附加险-->
					<div class="customertext">附加险年交费合计（每1000元保险金额对应10元保险费，要求输入为10的整数倍，单位：元）：</div>
                    <input class="premium_input1" id="premium_input_f3_a" type="number" data-mini="true" value="0" min="0" step="10"/>
					<span class="premiumalert_ddb"><span span id="remind_f3"></span> (工具仅做自动核保范围内的演示)</span><br/><hr/>
		</div> 
        <div class="ui-block-a getguaranteediv"><div  data-role="button" id="getguarantee_fu3">计算保险利益</div></div><br/><br/>
    </div>
    <div class="oneblankdiv"></div>
    <div class="blankdiv"></div>
    <div data-role="footer" class="footer">
    	<div class="footertext">中邮保险销售辅助工具V3.0.2</div>
    </div>
</div> <!--End of page fu3info -->



<!---fu3 info-->
<div data-role="page" id="fu3guarantee"  class="info">
    <div data-role="content">
		 <div style=" position: absolute;line-height:1em;right:0.5%;top:0.5em;"><a  data-add-back-btn="true" style="font-size:1em;"   data-rel="back" data-icon="back" data-role="button" class="ui-btn-right" >返回</a></div><br/>
        <div><span class="title">产品亮点：</span> 富富余3号<br/>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 分红生财图个富!<br/><br/>
        </div>
        <div>
        	<span class="title">基本信息:</span>富富余3号两全保险(分红型);&nbsp;&nbsp;被保险人年龄<span id="showage_f3" class="parameter"> </span>岁
        	<div class="maininfotable">	        	
	    		 <table data-role="table"  class="ui-body-d ui-shadow table-stripe ui-responsive"  >
			         <thead>
			           <tr class="ui-bar-d"><th>保险名称</th><th>交费期间</th><th>保险期间</th><th>所交保费</th><th >基本保额</th>
			           </tr>
			         </thead>
			         <tbody>          
			           <tr>	   
						 <td><span class="parameter">富富余3号</span></td>
			             <td><span id="payperiodmain_f3" class="parameter"> </span></td>
			             <td><span class="parameter">6年 </span></td>
			             <td><span id="mainpremium_f3" class="parameter"></span></td>
			             <td><span id="coverage_f3" class="parameter"></span></td>	             
			           </tr>	
						<tr>	          
						 <td><span class="parameter">中邮附加意外伤害医疗保险</span></td>  
						<td><span id="payperiodaddtional_f3" class="parameter"> </span></td>
			           	<td><span class="parameter">6年 </span></td>
			           	<td><span id="additionalpremium_f3" class="parameter"> </span></td>
			           	<td><span id="additionalcoverage_f3" class="parameter"> </span></td>
			           </tr>						  
						<tr>
			           	<td><span class="parameter">年交费合计：</span></td>
			           	<td>&nbsp;</td>
			           	<td>&nbsp;</td>
			           	<td><span id="totalpremium" class="parameter">10000元</span></td>
			           	<td>&nbsp;</td>
			           </tr>
			         </tbody>
		       </table> 
	       </div> 
		</div>
			<div>
        	 <div class="title">一、保险责任：</div>
			 <div class="title2">(一)满期保险金</div>
             <div class="coveragetext">保险合同期满，领取满期金<span class="coverage" id="f_all_coverage_f3"></span>元，本合同效力终止。</div>
             <div class="title2">(二)身故、全残保险金金</div>
             <div class="coveragetext">被保险人自保险责任开始日或本合同复效日(以较迟者)为准起<span  class="parameter">1 年后</span>因非意外原因导身故或全残，按照基本保额乘以交费年度数给付"身故、全残保险金"，最低给付<span class="coverage" id="unexpectedgomin_f3_a"></span>元，最高给付<span class="coverage" id="unexpectedgomax_f3_a"></span>元，合同效力终止。</div>
             <div class="alert">&nbsp;&nbsp;&nbsp;被保险人自保险责任开始日或本合同复效日(以较迟者为准)起<span  class="parameter">1 年内</span>因非意外原因导身故或全残，将按照本合同约定的基本保险金额与105%首期保费的较大者给付"身故、全残保险金"<span class="coverage" id="mainpremium_f3_a"></span>元,本合同效力终止。</div>
             <div class="title2">(三)意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起因意外导致身故或全残，按照基本保额乘以交费年度数的<span  class="parameter">2倍</span>给付"身故、全残保险金"，最低给付<span class="coverage" id="unexpectedgomin_f3_b"></span>元，最高给付<span class="coverage" id="unexpectedgomax_f3_b"></span>元，本合同效力终止。</div>   
			 <div class="title2">(四)交通意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起搭乘指定公共交通工具（见合同条款）期间遭受意外伤害事故导致身故或全残，按照基本保额乘以交费年度数的<span  class="parameter">3倍</span>给付"交通意外身故、全残保险金"，最低给付<span class="coverage" id="unexpectedgomin_f3_c"></span>元，最高给付<span class="coverage" id="unexpectedgomax_f3_c"></span>元，本合同效力终止。</div> <br/>
			 <div class="title2">(五)意外伤害医疗保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起遭受意外伤害事故，按条款给付保险责任期间遭受意外伤害而发生的合理且必要的医疗费用<span class="coverage" id="aditionalRiskGuarante_f3"></span>元，本合同效力终止。</div> <br/>
			<div class="title">二、合同权益</div>
             <div class="title2">保单红利</div>
            <div class="coveragetext">可对分红选择累积生息，享受高速发展的中邮保险的经营成果。</div><br/>
			<div class="alert">本计划书仅供参考，详细内容以保险合同条款为准。</div><hr/>
            <div class="manger">理财经理：<span style="padding-left:6em">服务电话：</span></div><br/><br/> 
			</div>
		</div>
		<br><br>	
		
		<!--<nav id="menu">
				<ul>
					<li><a href="../index.html">回到主页</a></li>
					<li><a href="../html/case.html#fu1_case" rel="external">案例_富富余1号</a></li>
					<li><a href="../html/code.html#occu_submit3" rel="external">职业代码_模糊查询</a></li>
					<li><a href="../html/fu3.html" rel="external">富富余3号</a></li>
					<li><a href="../html/renhe.html" rel="external">核保资料</a></li>
					<li><a href="../html/mian2.html" rel="external">绵绵寿2号</a></li>
				</ul>
		</nav>-->
		
		<div data-role="footer" class="footer">
			<div class="footertext">中邮保险销售辅助工具V3.0.2</div>
		</div>
</div><!-- end of fu3 guarantee -->
