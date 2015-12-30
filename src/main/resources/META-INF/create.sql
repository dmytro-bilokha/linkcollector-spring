CREATE TABLE `SEARCH_QUERY` ( `QUERY_HASH` bigint(20) NOT NULL, `TIME_PERSIST` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, PRIMARY KEY (`QUERY_HASH`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `WEB_RESULT` ( `ID` int(11) NOT NULL AUTO_INCREMENT, `TITLE` varchar(150) NOT NULL, `URL` varchar(255) NOT NULL, `DISPLAY_URL` varchar(50) NOT NULL, `DESCRIPTION` varchar(255) DEFAULT NULL, `QUERY_HASH` bigint(20) NOT NULL, PRIMARY KEY (`ID`), KEY `QUERY_HASH` (`QUERY_HASH`), CONSTRAINT `WEB_RESULT_FK` FOREIGN KEY (`QUERY_HASH`) REFERENCES `SEARCH_QUERY` (`QUERY_HASH`) ON DELETE CASCADE) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `SCORING_RESULT` ( `TAGS_HASH` bigint(20) NOT NULL, `WEB_RESULT_ID` int(11) NOT NULL, `SCORE` int(11) NOT NULL, PRIMARY KEY (`TAGS_HASH`,`WEB_RESULT_ID`), KEY `WEB_RESULT_ID` (`WEB_RESULT_ID`), CONSTRAINT `SCORING_RESULT_FK` FOREIGN KEY (`WEB_RESULT_ID`) REFERENCES `WEB_RESULT` (`ID`) ON DELETE CASCADE) ENGINE=InnoDB DEFAULT CHARSET=utf8;
