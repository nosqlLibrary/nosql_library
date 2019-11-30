/*
 Navicat Premium Data Transfer

 Source Server         : yun
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : library

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 29/11/2019 22:02:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_table
-- ----------------------------
DROP TABLE IF EXISTS `admin_table`;
CREATE TABLE `admin_table`  (
  `admin_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_pwd` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE INDEX `admin_name`(`admin_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_table
-- ----------------------------
INSERT INTO `admin_table` VALUES ('5076941234', '丽丽', '123');
INSERT INTO `admin_table` VALUES ('6819861234', '张三', '123');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `b_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `isbn` char(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `b_status` enum('A','B','C','D') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`b_id`) USING BTREE,
  INDEX `isbn_fk`(`isbn`) USING BTREE,
  CONSTRAINT `isbn_fk` FOREIGN KEY (`isbn`) REFERENCES `book_info` (`isbn`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('013278', '9787208159228', 'A');
INSERT INTO `book` VALUES ('036313', '9787508647357', 'A');
INSERT INTO `book` VALUES ('040635', '9787540484651', 'A');
INSERT INTO `book` VALUES ('072688', '9787544258975', 'A');
INSERT INTO `book` VALUES ('117692', '9787508647357', 'A');
INSERT INTO `book` VALUES ('119294', '9787115373557', 'A');
INSERT INTO `book` VALUES ('136751', '9787544270878', 'C');
INSERT INTO `book` VALUES ('175009', '9787540485696', 'A');
INSERT INTO `book` VALUES ('186284', '9787508647357', 'A');
INSERT INTO `book` VALUES ('209291', '9787530219843', 'A');
INSERT INTO `book` VALUES ('230046', '9787540484651', 'A');
INSERT INTO `book` VALUES ('233412', '9787544270878', 'B');
INSERT INTO `book` VALUES ('261820', '9787208148536', 'B');
INSERT INTO `book` VALUES ('283099', '9787540485696', 'D');
INSERT INTO `book` VALUES ('317084', '9787544258975', 'A');
INSERT INTO `book` VALUES ('328426', '9787559413727', 'A');
INSERT INTO `book` VALUES ('354757', '9787535681942', 'B');
INSERT INTO `book` VALUES ('368884', '9787540485528', 'B');
INSERT INTO `book` VALUES ('389308', '9787208148536', 'A');
INSERT INTO `book` VALUES ('404562', '9787571102845', 'A');
INSERT INTO `book` VALUES ('446575', '9787208148536', 'B');
INSERT INTO `book` VALUES ('446989', '9787544258975', 'B');
INSERT INTO `book` VALUES ('453647', '9787540484651', 'B');
INSERT INTO `book` VALUES ('495494', '9787115373557', 'A');
INSERT INTO `book` VALUES ('507114', '9787540484651', 'A');
INSERT INTO `book` VALUES ('518022', '9787540484651', 'A');
INSERT INTO `book` VALUES ('547673', '9787544258975', 'A');
INSERT INTO `book` VALUES ('614639', '9787559413727', 'A');
INSERT INTO `book` VALUES ('620115', '9787208159228', 'A');
INSERT INTO `book` VALUES ('633230', '9787540485528', 'C');
INSERT INTO `book` VALUES ('637984', '9787213091452', 'A');
INSERT INTO `book` VALUES ('662845', '9787544258975', 'A');
INSERT INTO `book` VALUES ('672173', '9787208148536', 'A');
INSERT INTO `book` VALUES ('676672', '9787544270878', 'A');
INSERT INTO `book` VALUES ('693894', '9787208148536', 'A');
INSERT INTO `book` VALUES ('701944', '9787544270878', 'A');
INSERT INTO `book` VALUES ('702213', '9787020139927', 'A');
INSERT INTO `book` VALUES ('729751', '9787508647357', 'A');
INSERT INTO `book` VALUES ('733551', '9787208148536', 'A');
INSERT INTO `book` VALUES ('735772', '9787544258975', 'A');
INSERT INTO `book` VALUES ('776773', '9787115373557', 'A');
INSERT INTO `book` VALUES ('778155', '9787544258975', 'A');
INSERT INTO `book` VALUES ('785935', '9787559413727', 'A');
INSERT INTO `book` VALUES ('839217', '9787532728893', 'A');
INSERT INTO `book` VALUES ('874156', '9787540484651', 'A');
INSERT INTO `book` VALUES ('893126', '9787540485696', 'A');
INSERT INTO `book` VALUES ('956343', '9787559614636', 'A');
INSERT INTO `book` VALUES ('991270', '9787509513729', 'A');

-- ----------------------------
-- Table structure for book_info
-- ----------------------------
DROP TABLE IF EXISTS `book_info`;
CREATE TABLE `book_info`  (
  `isbn` char(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `b_na` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `b_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `b_aut` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `press` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pdate` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `b_pnum` int(11) NULL DEFAULT NULL,
  `b_date` date NOT NULL,
  `b_num` int(11) NOT NULL,
  `b_intro` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`isbn`) USING BTREE,
  INDEX `b_type`(`b_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_info
-- ----------------------------
INSERT INTO `book_info` VALUES ('9787020139927', '失踪的孩子', 'I', '[意] 埃莱娜·费兰特 ', '99读书人|人民文学出版社', '2018-7', '48元', 480, '2019-07-15', 2, NULL);
INSERT INTO `book_info` VALUES ('9787115373557', '数学之美 （第二版）', 'O', '吴军', '人民邮电出版社', '2014-11', '49.00元', 312, '2019-07-29', 3, NULL);
INSERT INTO `book_info` VALUES ('9787208148536', '奥古斯都', 'K', '[美] 约翰·威廉斯', '上海人民出版社', '2018-5', '56元', 374, '2019-07-15', 6, NULL);
INSERT INTO `book_info` VALUES ('9787208159228', '在别人的句子里', 'I', '陈以侃', '上海人民出版社', '2019-8-1', '48.00元', 248, '2019-10-09', 2, '☆《毛姆短篇小说全集》《海风中失落的血色馈赠》译者陈以侃，接引英美文坛新风，挥洒一流文字趣味。\n☆ 捕捉阅读时的每一次动心。投身字里行间，每一句都是热恋。\n☆ Book-chat is still the best game in town.\n这里什么都没有，只有读过残留的快感。评判一个作者合不合自己的心意，先别翻那本大部头，找篇聊阅读聊作家的文章，要是读来还没有下楼取快递心潮澎湃，那就先搁置一旁吧。\n前编辑、现翻译兼评论，总之靠写字吃饭的陈以侃，把阅读时的动心捕捉为文字，引诱我们直面文学、相信文学，再相信阅读。\n---------------------------------------------------------------------\n——你读过这本书吗？\n——不算亲身读过。\n写出这本《一年危险阅读》的安迪·米勒，如果跟我在一个单位，恐怕会成为饭搭子。我们不但都认清，“此生非读不可的书”，恐怕八十辈子都读不完；而且，那些我们骗人家读过的书，用余生去补也早已无望了。后来他也去做了编辑，但在儿子出生后的两年里，意识到工作之外，只读过一本书。丹·布朗的《达·芬奇密码》。这就很严重了。\n这本书最好的地方，在于他要你相信，好书已经足够好了，你要舍得辛苦。我也认同，阅读的一大销魂之处，是某个从来没有想过要讨好你的作家，在熬到百来页的时候，突然跟你勾肩搭背引为知己，不管你朝哪边看，都是四目相接；不管你怎么跑，都跟他踩在同一个步点上。\n---------------------------------------------------------------------\n陈以侃在以一位真正的读者的身份，去创造一种作者文本，通过称叹、训斥与从不间断的调试，去接近那些难以言喻的瞬间，某个自我的核心。与此同时，他还拥有一副那么迷人的腔调：无比桀骜，又像在密谋。使人想要痛击，或者与之痛饮一场。——班宇，作家\n陈以侃下笔有一种罕见的、时刻具有自觉意识的诚实——其实这很难，因为它一不小心就会被理解成自恋。读完这本书，在收获了无数让人为之击节抑或陷入沉思的见识之余，我也清晰地看到一个倔强的、试图从平稳持重中突围而出的写作者的轨迹。他熟读经典，却也质疑经典；他迷恋技术，却也解构技术——他永远更敏感于捕捉浩瀚文本里的那一点僭越的灵光。——黄昱宁，作家\n我羡慕陈老师总是可以奋不顾身、毫无保留地投入他喜欢的作家和文本，也羡慕他转身又能找到描述和评价这种热爱的距离和准确性。他本质上和他写的那些有趣而有才能的灵魂是一类人。或者让我再诚实一点，这种羡慕其实已经严重到了嫉妒的程度。——吴琦，《单读》主编\n任何编辑能够拥有陈以侃这样的作者，都是一大幸事。对我来说，拿到他的稿件之后，除了必要的技术处理，几乎不用任何改动，剩下要做的，无非就是欣赏他炼字锻句的工夫与推敲琢磨的巧思。我觉得他的这本文集，除了提示作者可以怎样写之外，更大的作用还在于提示读者可以怎样读。所以，任何作者拥有陈以侃这样的读者，也是一大幸事。——郑诗亮，《上海书评》执行主编\n你可真敢胡来，陈老师那是多厉害的人和书，要我多嘴。——苗炜，作家');
INSERT INTO `book_info` VALUES ('9787213091452', '黄雀记', 'I', '苏童', '浙江人民出版社', '2019-4', '52.00元', 0, '2019-10-09', 1, '香椿街上的三个青年，一场扑朔迷离的强奸风波，\n一个人的十年冤狱和三个人命运的罪与罚。\n《黄雀记》全书分为三章：保润的春天、柳生的秋天、白小姐的夏天。作者通过三个不同当事人的视角，书写了这三个受侮辱与被损害的人的成长与碰撞。\n十年后，年少时犯下的错，错的时间，命运的手，让这三个人兜兜转转还是纠缠在了一起。命运迫使他们发现，尽管物是人非斗转星移，他们依然不得不去面对过去的巨大伤痛。\n过去的终究不会过去，该还的终是要还。他们必须以自己的方式，清算当年留下的罪孽……');
INSERT INTO `book_info` VALUES ('9787508647357', '人类简史', 'N', '[以色列] 尤瓦尔·赫拉利 ', '中信出版社', '2014-11', ' CNY 68.00', 440, '2019-07-29', 4, NULL);
INSERT INTO `book_info` VALUES ('9787509513729', '证券市场基础知识', 'F', NULL, NULL, '2009-5', '36.00元', 355, '2019-10-30', 1, '《证券市场基础知识(SAC)》主要讲述了：为适应资本市场发展的需要，中国证券业协会根据一年来法律法规的变化和市场的发展，对《证券业从业资格考试统编教材》进行了修订：第一，根据证券公司监管法规和实务操作中的变化，增补或修改相应内容，包括证券公司合规管理相关制度、证券公司风险控制指标、与证券公司业务及设立分支机构相关的内容等；第二，根据新修订的《证券发行上市保荐业务管理办法》修订有关发行保荐制度的内容；第三，根据新发布的《首次公开发行股票并在创业板上市管理暂行办法》，增加有关内容；第四，对《证券交易》书稿进行全面梳理，调整了部分章节设置，并根据新颁布的法规对相关内容进行了修订和补充；第五，根据新发布的有关基金销售的规范，修订《证券投资基金》中相关内容，并增加了有关基金财务会计报告分析的内容，修订了基金估值的相关内容；第六，对原教材中的基本概念、理念和基本框架进行了较为全面的梳理和修订，对错漏之处进行更正，删除了已不再适用的内容。');
INSERT INTO `book_info` VALUES ('9787530219843', '热带', 'I', '李唐', '北京出版集团·北京十月文艺出版社', '2019-10', '49.00元', 264, '2019-10-30', 1, '✔ “紫金·人民文学之星”得主、2018《收获》杂志重磅推荐青年作家李唐全新短篇小说集\n✔ 格非、阿乙、徐则臣、杨葵、邱华栋、杨庆祥一致力荐的文学新力量！\n✔奇趣的故事，怪异的人物，天马行空的少年想象，自成一体的奇幻世界，用白日梦式的脑洞，对抗生活中日复一日的常规与无聊\n——————————————————————\n在婚礼前夕冒雨去看迷路的鲸鱼；\n生活在废弃的游乐园中，每日被“死亡”啃食生活领域；\n在世界尽头的小镇里，有能自己打字的打字机，诱拐少女的长颈鹿，突如其来的松果雨，和掉落在路边草丛的闪电……\n8个超现实故事，8种奇遇，8个异想空间，作者运用丰富的想象力和诗性的文字，创造出一个迷幻、神秘，又充斥着少年心气的奇幻世界。\n——————————————————————\n从李唐的作品中，可以看出新一代作家重塑自我的艰辛努力和别样路径。他的叙事明晰、清新，富有创造力。——格非\n很久没看到李唐这样在30岁以前就成熟起来的作家。——阿乙\n李唐的小说表达了他试图梳理复杂生活的愿望，在诸多超现实的元素里，深藏着强烈的“现实感”。——徐则臣\n');
INSERT INTO `book_info` VALUES ('9787532728893', '舞！舞！舞！', 'I', '[日] 村上春树', '上海译文出版社', '2002-6', '25.00元', 502, '2019-10-09', 1, '六具白骨摆列在一间亦真亦幻的死亡之屋里。“我”的老朋友“鼠”已经死于寻羊冒险。“我”好不容易找到了投缘的老同学五反田，可这位被演艺界包装得精神分裂的电影明星，却接连勒死了两名高级应召女郎，自己也驾着高级跑车“玛莎拉蒂”葬身大海。孤独的女孩“雪”和她孤傲的母亲“雨”，虽然还能在这疯狂世界上勉强生存，可“雨”的守护神笛克，却也躲不过一场莫名的车祸。度过了一段死亡陪伴的惊魂日子，“我”终于在宾馆女服务员由美吉那里找到了安全感，也有了在安静城市过安静生活的具体计划。可是，那第六具白骨到底意味着谁呢？“我”依然脱不出死亡之屋。\n“高度发达的资本主义社会，”村上春树把这句话重复了不知多少遍，他是想寻找所有这些死亡和罪恶的源头吧？');
INSERT INTO `book_info` VALUES ('9787535681942', '追寻逝去的时光·第一卷：去斯万家那边', 'I', '[法] 马塞尔·普鲁斯特 原著 / 斯泰凡·厄埃 绘', '后浪丨湖南美术出版社', '2018-1', '160元', 224, '2019-07-15', 1, NULL);
INSERT INTO `book_info` VALUES ('9787540484651', '如父如子', 'I', '[日] 是枝裕和 / [日] 佐野晶 ', '湖南文艺出版社', '2018-4', '49.8元', 304, '2019-07-15', 6, NULL);
INSERT INTO `book_info` VALUES ('9787540485528', '莫斯科绅士', 'I', '[美]埃默·托尔斯 ', '博集天卷 | 湖南文艺出版社', '2018-6-15', '79.8元', 592, '2019-07-15', 3, NULL);
INSERT INTO `book_info` VALUES ('9787540485696', '观山海', 'I', '杉泽 / 梁超', '博集天卷 | 湖南文艺出版社', '2018-6', '168元', 415, '2019-07-15', 5, NULL);
INSERT INTO `book_info` VALUES ('9787544258975', '霍乱时期的爱情', 'I', '[哥伦比亚] 加西亚·马尔克斯 ', '南海出版公司', '2012-9-1', '39.50元', 401, '2019-07-16', 9, NULL);
INSERT INTO `book_info` VALUES ('9787544270878', '解忧杂货店', 'I', '[日] 东野圭吾', '南海出版公司', '2014-5', '39.50元', 291, '2019-07-16', 4, '现代人内心流失的东西，这家杂货店能帮你找回——\r\n\r\n僻静的街道旁有一家杂货店，只要写下烦恼投进卷帘门的投信口，第二天就会在店后的牛奶箱里得到回答。\r\n\r\n因男友身患绝症，年轻女孩静子在爱情与梦想间徘徊；克郎为了音乐梦想离家漂泊，却在现实中寸步难行；少年浩介面临家庭巨变，挣扎在亲情与未来的迷茫中……\r\n\r\n他们将困惑写成信投进杂货店，随即奇妙的事情竟不断发生。\r\n\r\n生命中的一次偶然交会，将如何演绎出截然不同的人生？\r\n\r\n如今回顾写作过程，我发现自己始终在思考一个问题：站在人生的岔路口，人究竟应该怎么做？我希望读者能在掩卷时喃喃自语：我从未读过这样的小说。——东野圭吾');
INSERT INTO `book_info` VALUES ('9787559413727', '我们一无所有', 'I', '[美] 安东尼·马拉', '江苏凤凰文艺出版社', '2018-2', '49.8元', 332, '2019-07-15', 3, NULL);
INSERT INTO `book_info` VALUES ('9787559614636', '房思琪的初恋乐园', 'I', '林奕含', ' 北京联合出版公司', '2018-1', '45元', 272, '2019-07-15', 1, NULL);
INSERT INTO `book_info` VALUES ('9787571102845', '如何写出高转化率文案', 'G', '[英] 安迪·马斯伦 / Andy Maslen', '后浪丨大象出版社', '2019-11', '48.00元', 264, '2019-10-30', 1, '从吸睛到吸金\n运用心理学法则写出好文案\n◎ 编辑推荐\n☆ 无论你从事广告、营销、新媒体还是知识付费，懂得高品质文案持久变现的黄金窍门，就能打造精品与畅销品，就能靠写作赚钱！\n☆ 用更巧妙、更高级、更不露声色的方式收割大众的购买欲！深入消费者的大脑，运用心理学、情感学和认 知科学，让路人成为读者，让读者成为潜在客户，让潜在客户成为客户！\n☆ 了解消费者做出购买决定的深层驱动力，你也能写出“走心”“戳心”“深入人心”的吸金文案，引导消费者做出“感觉正确”的购买决定，让文案引爆流量与销量！\n☆ 25条心理学法则，100多种表达方式，75组实践练习，10多个真实案例，从吸引、影响，到说服、销售——手把手教你一步一步写出超级“带货”的文案！\n☆ 强调产品的功能，不如强调产品对客户的重要性\n☆ 从客户的感受出发，用故事调动兴趣，以共情创造关联\n☆ 情绪导向的文案，可以更好地驱动读者做出“感觉正确”的购买决定\n☆ 不要低估“恭维”的力量，好的文案让花钱看起来像一种“特权”\n◎ 内容简介\n记住，写文案的目的不是展示才华，而是把产品卖出去！想写出“高转化率”文案，我们需要了解消费者的购买心理，还需要了解产生购买心理的深层驱动力。科学家们发现，人类总是以情感为基础做决定，随后再将这些决定合理化，消费者在做出购买决定时，这一点往往更加明显。而文案写作者，恰恰可以利用这一点来引导读者，让读者做出“感觉正确”的购买选择。\n本书将带你深入客户的大脑，学习如何利用读者最深层的心理驱动力来进行文案创作，你还将学到如何用讲故事的方法进行销售，以及如何说服人们参与到你的市场测试中。全书包含10多个真实的案例研究；25个心理文案写作技巧；75个实践练习；100多种引发情绪的表达方式。通过示例、练习和提示，文案“大咖”安迪·马斯伦将为你传授更高级的文案写作技能，帮助你磨炼写作与沟通技巧，写出“走心”“带货”的好文案。\n◎ 名人推荐\n安迪比大多数人更了解如何推销。这是一本清晰、全面、科学、强大的文案创作指南。只要几英镑和一天的阅读就能换来一个文案高手，这对你来说是一笔不错的交易，买这本书吧。\n——汤姆·奥尔布莱顿（Tom Albrighton），ABC文案创始人，职业文案网联合创始人\n这本书收集的是只有文案大师才知道的秘密，全是提炼而来的明智而清晰的建议。但是，看完好好保密就行，我们可不希望每个人都知道这些诀窍。\n——蒂姆·理查（Tim Rich），“写作协会”联合创始人\n');

-- ----------------------------
-- Table structure for booktype
-- ----------------------------
DROP TABLE IF EXISTS `booktype`;
CREATE TABLE `booktype`  (
  `bt_id` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bt_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`bt_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of booktype
-- ----------------------------
INSERT INTO `booktype` VALUES ('A', '马克思主义、列宁主义、毛泽东思想、邓小平理论');
INSERT INTO `booktype` VALUES ('B', ' 哲学、宗教');
INSERT INTO `booktype` VALUES ('C', '社会科学总论');
INSERT INTO `booktype` VALUES ('D', '政治、法律');
INSERT INTO `booktype` VALUES ('E', '军事');
INSERT INTO `booktype` VALUES ('F', '经济');
INSERT INTO `booktype` VALUES ('G', '文化、科学、教育、体育');
INSERT INTO `booktype` VALUES ('H', '语言、文字');
INSERT INTO `booktype` VALUES ('I', '文学');
INSERT INTO `booktype` VALUES ('J', '艺术');
INSERT INTO `booktype` VALUES ('K', ' 历史、地理');
INSERT INTO `booktype` VALUES ('N', '自然科学总论');
INSERT INTO `booktype` VALUES ('O', '数理科学和化学');
INSERT INTO `booktype` VALUES ('P', '天文学、地球科学');
INSERT INTO `booktype` VALUES ('Q', '生物科学');
INSERT INTO `booktype` VALUES ('R', '医药、卫生');
INSERT INTO `booktype` VALUES ('S', '农业科学');
INSERT INTO `booktype` VALUES ('T', ' 工业技术');
INSERT INTO `booktype` VALUES ('U', ' 交通运输');
INSERT INTO `booktype` VALUES ('V', '航空、航天');
INSERT INTO `booktype` VALUES ('X', '环境科学、劳动保护科学（安全科学）');
INSERT INTO `booktype` VALUES ('Z', '综合性图书');

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow`  (
  `id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `b_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `r_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `borrow_date` datetime(0) NOT NULL,
  `deadtime` datetime(0) NOT NULL,
  `return_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `b_id`(`b_id`) USING BTREE,
  INDEX `r_id`(`r_id`) USING BTREE,
  CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`b_id`) REFERENCES `book` (`b_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `borrow_ibfk_2` FOREIGN KEY (`r_id`) REFERENCES `reader` (`r_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES ('008240', '233412', '72756887607275688760', '2019-08-09 10:34:51', '2019-10-08 10:34:51', NULL);
INSERT INTO `borrow` VALUES ('228894', '446575', '86547233197275688760', '2019-07-23 21:35:40', '2019-10-21 21:35:40', NULL);
INSERT INTO `borrow` VALUES ('454918', '446989', '86547233197275688760', '2019-07-23 21:35:42', '2019-10-21 21:35:42', NULL);
INSERT INTO `borrow` VALUES ('504545', '036313', '72756887607275688760', '2019-08-09 10:34:33', '2019-10-08 10:34:33', '2019-10-30 21:13:17');
INSERT INTO `borrow` VALUES ('546546', '368884', '86547233197275688760', '2019-01-01 11:13:28', '2019-04-01 11:13:40', '2019-02-23 11:14:06');
INSERT INTO `borrow` VALUES ('553333', '136751', '86547233197275688760', '2018-11-23 11:14:27', '2019-02-23 11:14:37', '2019-01-10 11:14:54');
INSERT INTO `borrow` VALUES ('575598', '072688', '72756887607275688760', '2019-07-29 12:21:23', '2019-09-27 12:21:23', '2019-07-29 15:12:26');
INSERT INTO `borrow` VALUES ('634401', '354757', '72756887607275688760', '2019-07-23 21:34:26', '2019-09-21 21:34:26', NULL);
INSERT INTO `borrow` VALUES ('785886', '368884', '86547233197275688760', '2019-07-22 21:19:41', '2019-10-20 21:19:41', NULL);
INSERT INTO `borrow` VALUES ('804763', '261820', '72756887607275688760', '2019-07-23 21:34:24', '2019-09-21 21:34:24', NULL);
INSERT INTO `borrow` VALUES ('870797', '453647', '72756887607275688760', '2019-07-23 21:34:54', '2019-09-21 21:34:54', NULL);
INSERT INTO `borrow` VALUES ('982934', '136751', '72756887607275688760', '2019-07-22 21:12:53', '2019-09-20 21:12:53', NULL);

-- ----------------------------
-- Table structure for reader
-- ----------------------------
DROP TABLE IF EXISTS `reader`;
CREATE TABLE `reader`  (
  `r_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `r_na` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `r_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `r_sex` enum('f','m') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `r_col` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `r_tel` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `r_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `r_date` date NOT NULL,
  `r_status` enum('A','B','C') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `r_password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`r_id`) USING BTREE,
  INDEX `r_type`(`r_type`) USING BTREE,
  CONSTRAINT `reader_ibfk_1` FOREIGN KEY (`r_type`) REFERENCES `readertype` (`rt_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reader
-- ----------------------------
INSERT INTO `reader` VALUES ('05886752808889094041', 'tttt', '2', 'f', '12', '19869999901', 'dai@qq.com', '2019-10-29', 'C', '123');
INSERT INTO `reader` VALUES ('72756887607275688760', 'mary', '2', 'm', '外国语学院', '13400990099', '122@qq.com', '2019-07-21', 'A', '123');
INSERT INTO `reader` VALUES ('86547233197275688760', '浩浩', '1', 'f', '音乐学院', '12345123456', '123', '2019-07-20', 'A', '123');

-- ----------------------------
-- Table structure for readertype
-- ----------------------------
DROP TABLE IF EXISTS `readertype`;
CREATE TABLE `readertype`  (
  `rt_id` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rt_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rt_num` int(11) NOT NULL,
  `rt_bdate` int(11) NOT NULL,
  PRIMARY KEY (`rt_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of readertype
-- ----------------------------
INSERT INTO `readertype` VALUES ('1', '金牌会员', 12, 90);
INSERT INTO `readertype` VALUES ('2', '银牌会员', 11, 60);
INSERT INTO `readertype` VALUES ('3', '铜牌会员', 10, 30);

SET FOREIGN_KEY_CHECKS = 1;
