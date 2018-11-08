CREATE TABLE affiliation (
    affiliation_id character varying(45) NOT NULL,
    home character varying(45) NOT NULL,
    title character varying(100) DEFAULT NULL::character varying
);


ALTER TABLE affiliation OWNER TO dvnapp;

--
-- Data for Name: affiliation; Type: TABLE DATA; Schema: public; Owner: dvnapp
--

INSERT INTO affiliation VALUES ('ALGOMA', 'algoma', 'Algoma University');
INSERT INTO affiliation VALUES ('BROCK', 'brock', 'Brock University');
INSERT INTO affiliation VALUES ('CALGARY', 'calgary', 'University of Calgary');
INSERT INTO affiliation VALUES ('CARLETON', 'carleton', 'Carleton University');
INSERT INTO affiliation VALUES ('GUELPH', 'guelph', 'University of Guelph');
INSERT INTO affiliation VALUES ('LAKEHEAD', 'lakehead', 'Lakehead University');
INSERT INTO affiliation VALUES ('LAURENTIAN', 'laurentian', 'Laurentian University');
INSERT INTO affiliation VALUES ('LAURIER', 'laurier', 'Wilfrid Laurier University');
INSERT INTO affiliation VALUES ('MCMASTER', 'mcmaster', 'McMaster University');
INSERT INTO affiliation VALUES ('NIPISSING', 'nipissing', 'Nipissing University');
INSERT INTO affiliation VALUES ('OCAD', 'ocad', 'The Ontario College of Art & Design (OCAD)');
INSERT INTO affiliation VALUES ('OTTAWA', 'ottawa', 'University of Ottawa');
INSERT INTO affiliation VALUES ('QUEENS', 'queens', 'Queens University');
INSERT INTO affiliation VALUES ('REGINA', 'regina', 'University of Regina');
INSERT INTO affiliation VALUES ('RMC', 'rmc', 'Royal Military College of Canada');
INSERT INTO affiliation VALUES ('RYERSON', 'ryerson', 'Ryerson University');
INSERT INTO affiliation VALUES ('TORONTO', 'toronto', 'University of Toronto');
INSERT INTO affiliation VALUES ('TRENT', 'trent', 'Trent University');
INSERT INTO affiliation VALUES ('UOIT', 'uoit', 'University of Ontario Institute of Technology (UOIT)');
INSERT INTO affiliation VALUES ('WATERLOO', 'waterloo', 'University of Waterloo');
INSERT INTO affiliation VALUES ('WINDSOR', 'windsor', 'University of Windsor');
INSERT INTO affiliation VALUES ('WESTERN', 'westernu', 'Western University');
INSERT INTO affiliation VALUES ('YORK', 'york', 'York University');
INSERT INTO affiliation VALUES ('calgary', 'CALGARY', 'University of Calgary');


--
-- Name: affiliation affiliation_pkey; Type: CONSTRAINT; Schema: public; Owner: dvnapp
--

ALTER TABLE ONLY affiliation
    ADD CONSTRAINT affiliation_pkey PRIMARY KEY (affiliation_id);
--

CREATE TABLE affiliation_pattern (
    affiliation_pattern_id integer NOT NULL,
    affiliation_id character varying(45) NOT NULL,
    pattern character varying(100) DEFAULT NULL::character varying
);


ALTER TABLE affiliation_pattern OWNER TO dvnapp;

--
-- Name: affiliation_pattern_affiliation_pattern_id_seq; Type: SEQUENCE; Schema: public; Owner: dvnapp
--

CREATE SEQUENCE affiliation_pattern_affiliation_pattern_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE affiliation_pattern_affiliation_pattern_id_seq OWNER TO dvnapp;

--
-- Name: affiliation_pattern_affiliation_pattern_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dvnapp
--

ALTER SEQUENCE affiliation_pattern_affiliation_pattern_id_seq OWNED BY affiliation_pattern.affiliation_pattern_id;


--
-- Name: affiliation_pattern affiliation_pattern_id; Type: DEFAULT; Schema: public; Owner: dvnapp
--

ALTER TABLE ONLY affiliation_pattern ALTER COLUMN affiliation_pattern_id SET DEFAULT nextval('affiliation_pattern_affiliation_pattern_id_seq'::regclass);


--
-- Data for Name: affiliation_pattern; Type: TABLE DATA; Schema: public; Owner: dvnapp
--

INSERT INTO affiliation_pattern VALUES (1, 'ADMIN', '142.150.190.16');
INSERT INTO affiliation_pattern VALUES (2, 'ADMIN', '142.150.191.164');
INSERT INTO affiliation_pattern VALUES (3, 'ADMIN', '142.150.192.116');
INSERT INTO affiliation_pattern VALUES (4, 'ADMIN', '142.150.192.124');
INSERT INTO affiliation_pattern VALUES (5, 'ADMIN', '142.150.192.144');
INSERT INTO affiliation_pattern VALUES (6, 'ADMIN', '142.150.192.156');
INSERT INTO affiliation_pattern VALUES (7, 'ADMIN', '142.150.192.194');
INSERT INTO affiliation_pattern VALUES (8, 'ADMIN', '142.150.192.213');
INSERT INTO affiliation_pattern VALUES (9, 'ADMIN', '142.150.192.234');
INSERT INTO affiliation_pattern VALUES (10, 'ADMIN', '142.150.192.244');
INSERT INTO affiliation_pattern VALUES (11, 'ADMIN', '142.150.192.25');
INSERT INTO affiliation_pattern VALUES (12, 'ADMIN', '142.150.192.252');
INSERT INTO affiliation_pattern VALUES (13, 'ADMIN', '142.150.192.61');
INSERT INTO affiliation_pattern VALUES (14, 'ADMIN', '142.150.193.161');
INSERT INTO affiliation_pattern VALUES (15, 'ADMIN', '142.150.193.41');
INSERT INTO affiliation_pattern VALUES (16, 'ADMIN', '142.150.193.70');
INSERT INTO affiliation_pattern VALUES (17, 'ADMIN', '142.150.194.180');
INSERT INTO affiliation_pattern VALUES (18, 'ADMIN', '142.150.194.216');
INSERT INTO affiliation_pattern VALUES (19, 'ADMIN', '142.150.194.33');
INSERT INTO affiliation_pattern VALUES (20, 'ADMIN', '142.150.194.38');
INSERT INTO affiliation_pattern VALUES (21, 'ADMIN', '142.150.194.40');
INSERT INTO affiliation_pattern VALUES (22, 'ADMIN', '142.150.195.105');
INSERT INTO affiliation_pattern VALUES (23, 'ADMIN', '142.150.195.200');
INSERT INTO affiliation_pattern VALUES (24, 'ADMIN', '142.150.195.46');
INSERT INTO affiliation_pattern VALUES (25, 'ADMIN', '142.150.195.46');
INSERT INTO affiliation_pattern VALUES (26, 'ADMIN', '142.150.195.49');
INSERT INTO affiliation_pattern VALUES (27, 'ADMIN', '142.150.195.51');
INSERT INTO affiliation_pattern VALUES (28, 'ADMIN', '142.150.195.53');
INSERT INTO affiliation_pattern VALUES (29, 'ADMIN', '142.150.195.60');
INSERT INTO affiliation_pattern VALUES (30, 'ADMIN', '142.150.195.79');
INSERT INTO affiliation_pattern VALUES (31, 'ADMIN', '142.150.195.96');
INSERT INTO affiliation_pattern VALUES (32, 'ALGOMA', '173.239.137.120-127');
INSERT INTO affiliation_pattern VALUES (33, 'ALGOMA', '199.212.53-55.*');
INSERT INTO affiliation_pattern VALUES (34, 'BROCK', '*.brocku.ca');
INSERT INTO affiliation_pattern VALUES (35, 'BROCK', '139.57.*.*');
INSERT INTO affiliation_pattern VALUES (36, 'BROCK', '216.123.162.*');
INSERT INTO affiliation_pattern VALUES (37, 'CARLETON', '*.carleton.ca');
INSERT INTO affiliation_pattern VALUES (38, 'CARLETON', '134.117.1-109.*');
INSERT INTO affiliation_pattern VALUES (39, 'CARLETON', '134.117.10.200');
INSERT INTO affiliation_pattern VALUES (40, 'CARLETON', '134.117.111-135.*');
INSERT INTO affiliation_pattern VALUES (41, 'CARLETON', '134.117.138-140.*');
INSERT INTO affiliation_pattern VALUES (42, 'CARLETON', '134.117.142-255.*');
INSERT INTO affiliation_pattern VALUES (43, 'DENIED', '142.1.121.126');
INSERT INTO affiliation_pattern VALUES (44, 'GUELPH', '*.uoguelph.ca');
INSERT INTO affiliation_pattern VALUES (45, 'GUELPH', '131.104.*.*');
INSERT INTO affiliation_pattern VALUES (46, 'GUELPH', '207.261.39.158');
INSERT INTO affiliation_pattern VALUES (47, 'GUELPH', '64.201.162.64-79');
INSERT INTO affiliation_pattern VALUES (48, 'GUELPH', '68.171.65.192-223');
INSERT INTO affiliation_pattern VALUES (49, 'LAKEHEAD', '*.lakeheadu.ca');
INSERT INTO affiliation_pattern VALUES (50, 'LAKEHEAD', '65.39.0-63.*');
INSERT INTO affiliation_pattern VALUES (51, 'LAURENTIAN', '*.laurentian.ca');
INSERT INTO affiliation_pattern VALUES (52, 'LAURENTIAN', '*.uhearst.ca');
INSERT INTO affiliation_pattern VALUES (53, 'LAURENTIAN', '142.51.*.*');
INSERT INTO affiliation_pattern VALUES (54, 'LAURENTIAN', '192.26.235.*');
INSERT INTO affiliation_pattern VALUES (55, 'LAURENTIAN', '199.212.53.*');
INSERT INTO affiliation_pattern VALUES (56, 'LAURENTIAN', '199.212.55.*');
INSERT INTO affiliation_pattern VALUES (57, 'LAURENTIAN', '209.226.23.166');
INSERT INTO affiliation_pattern VALUES (58, 'LAURENTIAN', '209.226.56.124');
INSERT INTO affiliation_pattern VALUES (59, 'LAURENTIAN', '209.226.56.125');
INSERT INTO affiliation_pattern VALUES (60, 'LAURENTIAN', '209.226.56.126');
INSERT INTO affiliation_pattern VALUES (61, 'LAURENTIAN', '209.226.58.242');
INSERT INTO affiliation_pattern VALUES (62, 'LAURENTIAN', '209.226.58.243');
INSERT INTO affiliation_pattern VALUES (63, 'LAURENTIAN', '209.226.88.22');
INSERT INTO affiliation_pattern VALUES (64, 'LAURENTIAN', '209.226.88.26');
INSERT INTO affiliation_pattern VALUES (65, 'LAURIER', '*.wlu.ca');
INSERT INTO affiliation_pattern VALUES (66, 'LAURIER', '192.219.236-240.*');
INSERT INTO affiliation_pattern VALUES (67, 'LAURIER', '192.54.242.*');
INSERT INTO affiliation_pattern VALUES (68, 'LAURIER', '205.189.16-31.*');
INSERT INTO affiliation_pattern VALUES (69, 'LAURIER', '216.249.48-63.*');
INSERT INTO affiliation_pattern VALUES (70, 'LAURIER', '66.207.116.185-190');
INSERT INTO affiliation_pattern VALUES (71, 'LAURIER', '66.207.117.152-159');
INSERT INTO affiliation_pattern VALUES (72, 'LEANNE', '142.150.192.172');
INSERT INTO affiliation_pattern VALUES (73, 'MCMASTER', '*.mcmaster.ca');
INSERT INTO affiliation_pattern VALUES (74, 'MCMASTER', '130.113.*.*');
INSERT INTO affiliation_pattern VALUES (75, 'NIPISSING', '*.nipissingu.ca');
INSERT INTO affiliation_pattern VALUES (76, 'NIPISSING', '192.139.245.*');
INSERT INTO affiliation_pattern VALUES (77, 'NIPISSING', '192.197.167.*');
INSERT INTO affiliation_pattern VALUES (78, 'NIPISSING', '198.96.76.*');
INSERT INTO affiliation_pattern VALUES (79, 'NIPISSING', '198.98.76-79.*');
INSERT INTO affiliation_pattern VALUES (80, 'NIPISSING', '204.187.16-31.*');
INSERT INTO affiliation_pattern VALUES (81, 'NIPISSING', '206.248.120-129.*');
INSERT INTO affiliation_pattern VALUES (82, 'NIPISSING', '209.105.210.118');
INSERT INTO affiliation_pattern VALUES (83, 'OCAD', '*.ocad.on.ca');
INSERT INTO affiliation_pattern VALUES (84, 'OCAD', '132.174.250.145');
INSERT INTO affiliation_pattern VALUES (85, 'OCAD', '205.211.168.*');
INSERT INTO affiliation_pattern VALUES (86, 'OTTAWA', '*.uottawa.ca');
INSERT INTO affiliation_pattern VALUES (87, 'OTTAWA', '137.122.*.*');
INSERT INTO affiliation_pattern VALUES (88, 'OTTAWA', '172.24.*.*');
INSERT INTO affiliation_pattern VALUES (89, 'OTTAWA', '192.75.139.*');
INSERT INTO affiliation_pattern VALUES (90, 'OTTAWA', '206.248.224-235.*');
INSERT INTO affiliation_pattern VALUES (91, 'OTTAWA', '216.48.80.*');
INSERT INTO affiliation_pattern VALUES (92, 'OTTAWA', '216.48.81.*');
INSERT INTO affiliation_pattern VALUES (93, 'OTTAWA', '216.48.82.*');
INSERT INTO affiliation_pattern VALUES (94, 'OTTAWA', '216.48.83.*');
INSERT INTO affiliation_pattern VALUES (95, 'OTTAWA', '216.48.84.*');
INSERT INTO affiliation_pattern VALUES (96, 'OTTAWA', '216.48.85.*');
INSERT INTO affiliation_pattern VALUES (97, 'OTTAWA', '216.48.86.*');
INSERT INTO affiliation_pattern VALUES (98, 'OTTAWA', '216.48.87.*');
INSERT INTO affiliation_pattern VALUES (99, 'OTTAWA', '216.48.88.*');
INSERT INTO affiliation_pattern VALUES (100, 'OTTAWA', '216.48.89.*');
INSERT INTO affiliation_pattern VALUES (101, 'OTTAWA', '216.48.90.*');
INSERT INTO affiliation_pattern VALUES (102, 'OTTAWA', '216.48.91.*');
INSERT INTO affiliation_pattern VALUES (103, 'OTTAWA', '216.48.92.*');
INSERT INTO affiliation_pattern VALUES (104, 'OTTAWA', '216.48.93.*');
INSERT INTO affiliation_pattern VALUES (105, 'OTTAWA', '216.48.94.*');
INSERT INTO affiliation_pattern VALUES (106, 'OTTAWA', '216.48.95.*');
INSERT INTO affiliation_pattern VALUES (107, 'QUEENS', '*.queensu.ca');
INSERT INTO affiliation_pattern VALUES (108, 'QUEENS', '130.15.*.*');
INSERT INTO affiliation_pattern VALUES (109, 'QUEENS', '193.122.239.*');
INSERT INTO affiliation_pattern VALUES (110, 'RMC', '137.94.*.*');
INSERT INTO affiliation_pattern VALUES (111, 'RYERSON', '141.117.*.*');
INSERT INTO affiliation_pattern VALUES (112, 'TEST', '142.150.190.16');
INSERT INTO affiliation_pattern VALUES (113, 'TEST', '142.150.194.128');
INSERT INTO affiliation_pattern VALUES (114, 'TEST', '142.150.195.163');
INSERT INTO affiliation_pattern VALUES (115, 'TEST', '142.150.195.51');
INSERT INTO affiliation_pattern VALUES (116, 'TEST', '192.168.1.100');
INSERT INTO affiliation_pattern VALUES (117, 'TORONTO', '*.toronto.edu');
INSERT INTO affiliation_pattern VALUES (118, 'TORONTO', '*.utoronto.ca');
INSERT INTO affiliation_pattern VALUES (119, 'TORONTO', '128.100.*.*');
INSERT INTO affiliation_pattern VALUES (120, 'TORONTO', '138.51.*.*');
INSERT INTO affiliation_pattern VALUES (121, 'TORONTO', '140.98.120.246');
INSERT INTO affiliation_pattern VALUES (122, 'TORONTO', '140.98.210.243');
INSERT INTO affiliation_pattern VALUES (123, 'TORONTO', '142.1.*.*');
INSERT INTO affiliation_pattern VALUES (124, 'TORONTO', '142.150.*.*');
INSERT INTO affiliation_pattern VALUES (125, 'TORONTO', '142.150.190.131');
INSERT INTO affiliation_pattern VALUES (126, 'TORONTO', '142.151.*.*');
INSERT INTO affiliation_pattern VALUES (127, 'TORONTO', '142.46.224.137-139');
INSERT INTO affiliation_pattern VALUES (128, 'TORONTO', '173.48.165.40');
INSERT INTO affiliation_pattern VALUES (129, 'TORONTO', '192.12.183.*');
INSERT INTO affiliation_pattern VALUES (130, 'TORONTO', '192.139.206.*');
INSERT INTO affiliation_pattern VALUES (131, 'TORONTO', '192.139.207.*');
INSERT INTO affiliation_pattern VALUES (132, 'TORONTO', '192.75.177.*');
INSERT INTO affiliation_pattern VALUES (133, 'TORONTO', '192.75.254.*');
INSERT INTO affiliation_pattern VALUES (134, 'TORONTO', '192.82.128.*');
INSERT INTO affiliation_pattern VALUES (135, 'TORONTO', '192.82.129.*');
INSERT INTO affiliation_pattern VALUES (136, 'TORONTO', '192.82.130.*');
INSERT INTO affiliation_pattern VALUES (137, 'TORONTO', '192.82.131.*');
INSERT INTO affiliation_pattern VALUES (138, 'TORONTO', '192.87.158.242');
INSERT INTO affiliation_pattern VALUES (139, 'TORONTO', '198.96.61.*');
INSERT INTO affiliation_pattern VALUES (140, 'TORONTO', '199.212.31.*');
INSERT INTO affiliation_pattern VALUES (141, 'TORONTO', '209.175.55.*');
INSERT INTO affiliation_pattern VALUES (142, 'TORONTO', '24.123.6.130-133');
INSERT INTO affiliation_pattern VALUES (143, 'TORONTO', '68.236.111.27');
INSERT INTO affiliation_pattern VALUES (144, 'TORONTO', '99.202.104.66');
INSERT INTO affiliation_pattern VALUES (145, 'TRENT', '*.trentu.ca');
INSERT INTO affiliation_pattern VALUES (146, 'TRENT', '10.100.15.23');
INSERT INTO affiliation_pattern VALUES (147, 'TRENT', '192.197.151-154.*');
INSERT INTO affiliation_pattern VALUES (148, 'TRENT', '192.75.12.*');
INSERT INTO affiliation_pattern VALUES (149, 'TRENT', '204.225.8-15.*');
INSERT INTO affiliation_pattern VALUES (150, 'TRENT', '209.42.96-127.*');
INSERT INTO affiliation_pattern VALUES (151, 'UOIT', '192.197.54.136');
INSERT INTO affiliation_pattern VALUES (152, 'UOIT', '205.211.180.74');
INSERT INTO affiliation_pattern VALUES (153, 'UOIT', '205.211.181.105');
INSERT INTO affiliation_pattern VALUES (154, 'UOIT', '205.211.181.68');
INSERT INTO affiliation_pattern VALUES (155, 'WATERLOO', '*.uwaterloo.ca');
INSERT INTO affiliation_pattern VALUES (156, 'WATERLOO', '129.97.*.*');
INSERT INTO affiliation_pattern VALUES (157, 'WESTERN', '129.100.*.*');
INSERT INTO affiliation_pattern VALUES (158, 'WESTERN', '216.17.113.145');
INSERT INTO affiliation_pattern VALUES (159, 'WESTERN', '216.17.121.145');
INSERT INTO affiliation_pattern VALUES (160, 'WINDSOR', '*.uwindsor.ca');
INSERT INTO affiliation_pattern VALUES (161, 'WINDSOR', '137.207.*.*');
INSERT INTO affiliation_pattern VALUES (162, 'YORK', '*.yorku.ca');
INSERT INTO affiliation_pattern VALUES (163, 'YORK', '130.63.*.*');
INSERT INTO affiliation_pattern VALUES (164, 'YORK', '198.96.32-39.*');
INSERT INTO affiliation_pattern VALUES (165, 'YORK', '199.212.64-79.*');
INSERT INTO affiliation_pattern VALUES (166, 'YORK', '63.86.69.232');
INSERT INTO affiliation_pattern VALUES (167, 'YORK', '65.220.22.201');


--
-- Name: affiliation_pattern_affiliation_pattern_id_seq; Type: SEQUENCE SET; Schema: public; Owner: dvnapp
--

SELECT pg_catalog.setval('affiliation_pattern_affiliation_pattern_id_seq', 167, true);


--
-- Name: affiliation_pattern affiliation_pattern_pkey; Type: CONSTRAINT; Schema: public; Owner: dvnapp
--

ALTER TABLE ONLY affiliation_pattern
    ADD CONSTRAINT affiliation_pattern_pkey PRIMARY KEY (affiliation_pattern_id);


--
-- PostgreSQL database dump complete
--

