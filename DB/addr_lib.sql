﻿CREATE TABLE t_addr_lib (
  town VARCHAR(16) COLLATE utf8_general_ci,
  area VARCHAR(16) COLLATE utf8_general_ci NOT NULL,
  city VARCHAR(9) COLLATE utf8_general_ci DEFAULT NULL
)ENGINE=InnoDB
AVG_ROW_LENGTH=45 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
COMMIT;

/* Data for the `area` table  (Records 1 - 359) */

INSERT INTO `t_addr_lib` (`area`, `city`) VALUES 
  ('荔湾', '广州'),
  ('越秀', '广州'),
  ('海珠', '广州'),
  ('天河', '广州'),
  ('白云', '广州'),
  ('黄埔', '广州'),
  ('番禺', '广州'),
  ('花都', '广州'),
  ('南沙', '广州'),
  ('增城', '广州'),
  ('从化', '广州'),
  ('武江', '韶关'),
  ('浈江', '韶关'),
  ('曲江', '韶关'),
  ('始兴', '韶关'),
  ('仁化', '韶关'),
  ('翁源', '韶关'),
  ('乳源', '韶关'),
  ('新丰', '韶关'),
  ('乐昌', '韶关'),
  ('南雄', '韶关'),
  ('罗湖', '深圳'),
  ('福田', '深圳'),
  ('南山', '深圳'),
  ('宝安', '深圳'),
  ('龙岗', '深圳'),
  ('龙华', '深圳'),
  ('坪山', '深圳'),
  ('盐田', '深圳'),
  ('香洲', '珠海'),
  ('斗门', '珠海'),
  ('金湾', '珠海'),
  ('濠江', '汕头'),
  ('龙湖', '汕头'),
  ('金平', '汕头'),
  ('潮南', '汕头'),
  ('澄海', '汕头'),
  ('潮阳', '汕头'),
  ('南澳', '汕头'),
  ('禅城', '佛山'),
  ('顺德', '佛山'),
  ('南海', '佛山'),
  ('三水', '佛山'),
  ('高明', '佛山'),
  ('蓬江', '江门'),
  ('江海', '江门'),
  ('新会', '江门'),
  ('台山', '江门'),
  ('开平', '江门'),
  ('鹤山', '江门'),
  ('恩平', '江门'),
  ('赤坎', '湛江'),
  ('霞山', '湛江'),
  ('坡头', '湛江'),
  ('麻章', '湛江'),
  ('遂溪', '湛江'),
  ('徐闻', '湛江'),
  ('廉江', '湛江'),
  ('雷州', '湛江'),
  ('吴川', '湛江'),
  ('茂南', '茂名'),
  ('电白', '茂名'),
  ('高州', '茂名'),
  ('化州', '茂名'),
  ('信宜', '茂名'),
  ('端州', '肇庆'),
  ('鼎湖', '肇庆'),
  ('高要', '肇庆'),
  ('广宁', '肇庆'),
  ('怀集', '肇庆'),
  ('封开', '肇庆'),
  ('德庆', '肇庆'),
  ('四会', '肇庆'),
  ('惠城', '惠州'),
  ('惠阳', '惠州'),
  ('博罗', '惠州'),
  ('惠东', '惠州'),
  ('龙门', '惠州'),
  ('梅江', '梅州'),
  ('梅县', '梅州'),
  ('大埔', '梅州'),
  ('丰顺', '梅州'),
  ('五华', '梅州'),
  ('平远', '梅州'),
  ('蕉岭', '梅州'),
  ('兴宁', '梅州'),
  ('城区', '汕尾'),
  ('海丰', '汕尾'),
  ('陆河', '汕尾'),
  ('陆丰', '汕尾'),
  ('源城', '河源'),
  ('紫金', '河源'),
  ('龙川', '河源'),
  ('连平', '河源'),
  ('和平', '河源'),
  ('东源', '河源'),
  ('江城', '阳江'),
  ('阳东', '阳江'),
  ('阳西', '阳江'),
  ('阳春', '阳江'),
  ('清城', '清远'),
  ('清新', '清远'),
  ('佛冈', '清远'),
  ('阳山', '清远'),
  ('连山', '清远'),
  ('连南', '清远'),
  ('英德', '清远'),
  ('连州', '清远'),
  ('莞城', '东莞'),
  ('南城', '东莞'),
  ('万江', '东莞'),
  ('东城', '东莞'),
  ('石碣', '东莞'),
  ('石龙', '东莞'),
  ('茶山', '东莞'),
  ('石排', '东莞'),
  ('企石', '东莞'),
  ('横沥', '东莞'),
  ('桥头', '东莞'),
  ('谢岗', '东莞'),
  ('东坑', '东莞'),
  ('常平', '东莞'),
  ('寮步', '东莞'),
  ('大朗', '东莞'),
  ('黄江', '东莞'),
  ('清溪', '东莞'),
  ('塘厦', '东莞'),
  ('凤岗', '东莞'),
  ('长安', '东莞'),
  ('虎门', '东莞'),
  ('厚街', '东莞'),
  ('沙田', '东莞'),
  ('道滘', '东莞'),
  ('洪梅', '东莞'),
  ('麻涌', '东莞'),
  ('中堂', '东莞'),
  ('高埗', '东莞'),
  ('樟木头', '东莞'),
  ('大岭山', '东莞'),
  ('望牛墩', '东莞'),
  ('东区', '中山'),
  ('南区', '中山'),
  ('石岐', '中山'),
  ('西区', '中山'),
  ('五桂山', '中山'),
  ('火炬高技术产业开发区', '中山'),
  ('板芙', '中山'),
  ('大涌', '中山'),
  ('东凤', '中山'),
  ('东升', '中山'),
  ('阜沙', '中山'),
  ('港口', '中山'),
  ('古镇', '中山'),
  ('横栏', '中山'),
  ('黄圃', '中山'),
  ('民众', '中山'),
  ('南朗', '中山'),
  ('南头', '中山'),
  ('三角', '中山'),
  ('三乡', '中山'),
  ('沙溪', '中山'),
  ('神湾', '中山'),
  ('坦洲', '中山'),
  ('小榄', '中山'),
  ('湘桥', '潮州'),
  ('潮安', '潮州'),
  ('饶平', '潮州'),
  ('榕城', '揭阳'),
  ('揭东', '揭阳'),
  ('揭西', '揭阳'),
  ('惠来', '揭阳'),
  ('普宁', '揭阳'),
  ('云城', '云浮'),
  ('云安', '云浮'),
  ('新兴', '云浮'),
  ('郁南', '云浮'),
  ('罗定', '云浮'),
  ('茂港', '茂名'),
  ('火炬开发区', '中山'),
  ('荔湾区', '广州市'),
  ('越秀区', '广州市'),
  ('海珠区', '广州市'),
  ('天河区', '广州市'),
  ('白云区', '广州市'),
  ('黄埔区', '广州市'),
  ('番禺区', '广州市'),
  ('花都区', '广州市'),
  ('南沙区', '广州市'),
  ('增城区', '广州市'),
  ('从化区', '广州市'),
  ('武江区', '韶关市'),
  ('浈江区', '韶关市'),
  ('曲江区', '韶关市'),
  ('始兴县', '韶关市'),
  ('仁化县', '韶关市'),
  ('翁源县', '韶关市'),
  ('乳源瑶族自治县', '韶关市'),
  ('乳源县', '韶关市'),
  ('新丰县', '韶关市'),
  ('乐昌市', '韶关市'),
  ('南雄市', '韶关市'),
  ('罗湖区', '深圳市'),
  ('福田区', '深圳市'),
  ('南山区', '深圳市'),
  ('宝安区', '深圳市'),
  ('龙岗区', '深圳市'),
  ('龙华区', '深圳市'),
  ('坪山区', '深圳市'),
  ('盐田区', '深圳市'),
  ('香洲区', '珠海市'),
  ('斗门区', '珠海市'),
  ('金湾区', '珠海市'),
  ('濠江区', '汕头市'),
  ('龙湖区', '汕头市'),
  ('金平区', '汕头市'),
  ('潮南区', '汕头市'),
  ('澄海区', '汕头市'),
  ('潮阳区', '汕头市'),
  ('南澳县', '汕头市'),
  ('禅城区', '佛山市'),
  ('顺德区', '佛山市'),
  ('南海区', '佛山市'),
  ('三水区', '佛山市'),
  ('高明区', '佛山市'),
  ('蓬江区', '江门市'),
  ('江海区', '江门市'),
  ('新会区', '江门市'),
  ('台山市', '江门市'),
  ('开平市', '江门市'),
  ('鹤山市', '江门市'),
  ('恩平市', '江门市'),
  ('赤坎区', '湛江市'),
  ('霞山区', '湛江市'),
  ('坡头区', '湛江市'),
  ('麻章区', '湛江市'),
  ('遂溪县', '湛江市'),
  ('徐闻县', '湛江市'),
  ('廉江市', '湛江市'),
  ('雷州市', '湛江市'),
  ('吴川市', '湛江市'),
  ('茂南区', '茂名市'),
  ('电白区', '茂名市'),
  ('高州市', '茂名市'),
  ('化州市', '茂名市'),
  ('信宜市', '茂名市'),
  ('端州区', '肇庆市'),
  ('鼎湖区', '肇庆市'),
  ('高要区', '肇庆市'),
  ('广宁县', '肇庆市'),
  ('怀集县', '肇庆市'),
  ('封开县', '肇庆市'),
  ('德庆县', '肇庆市'),
  ('四会市', '肇庆市'),
  ('惠城区', '惠州市'),
  ('惠阳区', '惠州市'),
  ('博罗县', '惠州市'),
  ('惠东县', '惠州市'),
  ('龙门县', '惠州市'),
  ('梅江区', '梅州市'),
  ('梅县区', '梅州市'),
  ('大埔县', '梅州市'),
  ('丰顺县', '梅州市'),
  ('五华县', '梅州市'),
  ('平远县', '梅州市'),
  ('蕉岭县', '梅州市'),
  ('兴宁市', '梅州市'),
  ('海丰县', '汕尾市'),
  ('陆河县', '汕尾市'),
  ('陆丰市', '汕尾市'),
  ('源城区', '河源市'),
  ('紫金县', '河源市'),
  ('龙川县', '河源市'),
  ('连平县', '河源市'),
  ('和平县', '河源市'),
  ('东源县', '河源市'),
  ('江城区', '阳江市'),
  ('阳东区', '阳江市'),
  ('阳西县', '阳江市'),
  ('阳春市', '阳江市'),
  ('清城区', '清远市'),
  ('清新区', '清远市'),
  ('佛冈县', '清远市'),
  ('阳山县', '清远市'),
  ('连山壮族瑶族自治县', '清远市'),
  ('连南瑶族自治县', '清远市'),
  ('连山', '清远市'),
  ('连南', '清远市'),
  ('英德市', '清远市'),
  ('连州市', '清远市'),
  ('莞城区', '东莞市'),
  ('南城区', '东莞市'),
  ('万江区', '东莞市'),
  ('东城区', '东莞市'),
  ('石碣镇', '东莞市'),
  ('石龙镇', '东莞市'),
  ('茶山镇', '东莞市'),
  ('石排镇', '东莞市'),
  ('企石镇', '东莞市'),
  ('横沥镇', '东莞市'),
  ('桥头镇', '东莞市'),
  ('谢岗镇', '东莞市'),
  ('东坑镇', '东莞市'),
  ('常平镇', '东莞市'),
  ('寮步镇', '东莞市'),
  ('大朗镇', '东莞市'),
  ('黄江镇', '东莞市'),
  ('清溪镇', '东莞市'),
  ('塘厦镇', '东莞市'),
  ('凤岗镇', '东莞市'),
  ('长安镇', '东莞市'),
  ('虎门镇', '东莞市'),
  ('厚街镇', '东莞市'),
  ('沙田镇', '东莞市'),
  ('道滘镇', '东莞市'),
  ('洪梅镇', '东莞市'),
  ('麻涌镇', '东莞市'),
  ('中堂镇', '东莞市'),
  ('高埗镇', '东莞市'),
  ('樟木头镇', '东莞市'),
  ('大岭山镇', '东莞市'),
  ('望牛墩镇', '东莞市'),
  ('石岐区', '中山市'),
  ('五桂山区', '中山市'),
  ('火炬高技术产业开发区', '中山市'),
  ('板芙镇', '中山市'),
  ('大涌镇', '中山市'),
  ('东凤镇', '中山市'),
  ('东升镇', '中山市'),
  ('阜沙镇', '中山市'),
  ('港口镇', '中山市'),
  ('古镇镇', '中山市'),
  ('横栏镇', '中山市'),
  ('黄圃镇', '中山市'),
  ('民众镇', '中山市'),
  ('南朗镇', '中山市'),
  ('南头镇', '中山市'),
  ('三角镇', '中山市'),
  ('三乡镇', '中山市'),
  ('沙溪镇', '中山市'),
  ('神湾镇', '中山市'),
  ('坦洲镇', '中山市'),
  ('小榄镇', '中山市'),
  ('湘桥区', '潮州市'),
  ('潮安区', '潮州市'),
  ('饶平县', '潮州市'),
  ('榕城区', '揭阳市'),
  ('揭东区', '揭阳市'),
  ('揭西县', '揭阳市'),
  ('惠来县', '揭阳市'),
  ('普宁市', '揭阳市'),
  ('云城区', '云浮市'),
  ('云安区', '云浮市'),
  ('新兴县', '云浮市'),
  ('郁南县', '云浮市'),
  ('罗定市', '云浮市'),
  ('开发区', '中山'),
  ('连山县', '清远市'),
  ('连南县', '清远市'),
  ('开发区', '中山市');

COMMIT;

