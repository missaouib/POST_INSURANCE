select 
tp.form_no, tp.policy_no, 
DATE_FORMAT(tp.policy_date,'%Y%m%d') as ybt_date, 
DATE_FORMAT(tp.bill_back_date,'%Y%m%d') as bill_back_date,
DATE_FORMAT(tp.plicy_valid_date,'%Y%m%d') as policy_date,
cast(aes_decrypt(unhex(tpd.holder_card_num), 'GDPost') as char(100)) as holder_card_num, 
case tpd.holder_card_type 
when '身份证' then '10' 
when '其他' then '99' 
when '护照' then '51' 
when '台湾居民来往大陆通行证' then '61' 
when '港澳居民来往内地通行证' then '60' 
when '户口本' then '40' 
when '军人证' then '22' 
else '99' end as idtype, 
tp.holder, 
case tpd.holder_sexy when '男' then '1' else '2' end as syxy,
'CN' as gj,
'' as zjdqr,
'' as email,
cast(aes_decrypt(unhex(tpd.holder_mobile), 'GDPost') as char(100)) as h_mobile,
holder_postcode as h_postcode,
cast(aes_decrypt(unhex(tpd.holder_phone), 'GDPost') as char(100)) as h_phone,
'' as h_prov,
'' as h_city,
'' as h_area,
cast(aes_decrypt(unhex(tpd.holder_addr), 'GDPost') as char(100)) as h_addr,
'' as h_shouru,
'' as h_jtshouru,
'' as zhiyeleixing,
'' as zhiyedaima,
'' as zhiyemingch,
'' as birthday,
'' as h_hight,
'' as h_weight,
'' as h_marry,
'' as juminleixng,
case tpd.insured when tpd.holder then '本人' else '其他' end as guanxi,
'' as i_card_num,
'' as i_card_type,
cast(aes_decrypt(unhex(tpd.insured), 'GDPost') as char(100)) as i_name,
'' as i_sexy,
'' as i_guoji,
'' as i_zhengjyouxiaoqi,
'' as i_email,
'' as i_mobile,
'' as i_postcode,
'' as i_phone,
'' as i_prov,
'' as i_city,
'' as i_area,
'' as i_addr,
'' as i_shouru,
'' as i_zhiyeleixing,
'' as i_zhiyedaima,
'' as i_zhiyemingch,
'' as i_birthday,
'' as i_shengao,
'' as i_tizhong,
'' as i_hunyin,
'' as shouyi1_name,
'' as sy1_guanxi,
'' as sy1_sexy,
'' as sy1_birthday,
'' as sy1_cardtype,
'' as sy1_card_date,
'' as sy1_card_num,
'' as sy1_bili,
'' as sy1_phone,
'' as sy1_mobile,
'' as shouyi2_name,
'' as sy2_guanxi,
'' as sy2_sexy,
'' as sy2_birthday,
'' as sy2_cardtype,
'' as sy2_card_date,
'' as sy2_card_num,
'' as sy2_bili,
'' as sy2_phone,
'' as sy2_mobile,
tmc.duty_code as ybt_code,
tp.prod_name as xianzhongminghc,
'' as dangcijine,
'' as fenshu,
tp.perm as jiaofeiqijian,
'' as baoxianqijian,
tp.policy_fee as fee,
case tp2.prod_code 
when '123001' then '9976' 
when '123002' then '0981' 
when '123003' then '1624' 
when '120011' then '1615' 
else '' end as fjxcode,
tp2.prod_name as fjx1name,
'' as fjx1fs,
'' as fjx1fenshu,
tp2.perm as fjx1qijian,
'' as fjx1bxqj,
tp2.policy_fee as fjx1fee,
'' as fjx2code,
'' as fjx2name,
'' as fjx2fs,
'' as fjx2fenshu,
'' as fjx2qijian,
'' as fjx2bxqj,
'' as fjx2fee,
'' as wnx1,
'' as wnx2,
'' as wnx3,
tp.total_fee,
tp.fee_frequency,
'银行转账' as feetype,
'' as hongli,
'' as lingqufangshi,
'' as lingqunianling,
'' as lingquqijian,
tp.total_fee as pm,
tp.perm as sshouquqixian,
'' as shouquanqixian2,
case tp.fee_frequency 
when '1' then '一次性' 
else '每年' end as spl, 
'' as jiaofeizhanghao, 
'' as hetongzhengyi, 
'' as zhengyiweiyuan, 
'' as beizhu, 
'' as rengongdan, 
'' as fengxian, 
case tp.status 
when '有效' then '1' 
else '0' end as status 
from (t_policy tp 
left join t_policy_dtl tpd 
on tp.policy_no=tpd.policy_no) 
left join 
(select distinct duty_code as duty_code, match_code, perm from t_mtd_code) tmc 
on tp.prod_code=tmc.match_code and tp.perm=tmc.perm 
left join 
(select policy_no,prod_code,prod_name,policy_fee,perm from t_policy itp2 where itp2.policy_date>='2016-09-01' and itp2.attached_flag=1) tp2 
on tp.policy_no=tp2.policy_no 
where 
tp.attached_flag=0 and 
tp.policy_date>='2016-09-01' 
and tp.prod_code<>'150001' 
and tp.prod_code<>'121001' 
and tp.prod_code<>'121002' 
order by policy_date asc 
limit 20;