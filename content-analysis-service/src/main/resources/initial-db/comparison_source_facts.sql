INSERT INTO public.comparison_source_facts (comparison_summary_id, id, source_url) VALUES (1, 1, 'https://dziennik.com/trump-ujawnil-ktore-zalecenia-lekarzy-ignoruje-chce-miec-ladna-rzadka-krew/');
INSERT INTO public.comparison_source_facts (comparison_summary_id, id, source_url) VALUES (1, 2, 'https://wiadomosci.wp.pl/siniaki-na-dloniach-trumpa-zaskakujace-tlumaczenie-prezydenta-7238994732128768a');
INSERT INTO public.comparison_source_facts (comparison_summary_id, id, source_url) VALUES (1, 3, 'https://www.rmf24.pl/fakty/swiat/newsamp-od-siniakow-po-problemy-ze-snem-trump-o-tym-dlaczego-ignoruj,nId,8054769');
INSERT INTO public.comparison_source_facts (comparison_summary_id, id, source_url) VALUES (1, 4, 'https://wiadomosci.onet.pl/swiat/siniaki-na-dloniach-donalda-trumpa-rzeczniczka-bialego-domu-podala-zaskakujacy-powod/t2ldwwd');
INSERT INTO public.comparison_source_facts (comparison_summary_id, id, source_url) VALUES (2, 5, 'https://www.instagram.com/p/DSzLsM1jAKY/');
INSERT INTO public.comparison_source_facts (comparison_summary_id, id, source_url) VALUES (2, 6, 'https://www.instagram.com/reel/DSmH_lIlEUs/');
INSERT INTO public.comparison_source_facts (comparison_summary_id, id, source_url) VALUES (2, 7, 'https://www.facebook.com/enews/videos/enrique-iglesias-anna-kournikova-share-sweet-family-photo-after-welcoming-baby-n/1643646783371034/');
INSERT INTO public.comparison_source_facts (comparison_summary_id, id, source_url) VALUES (5, 15, 'https://www.instagram.com/p/DS4wZkmgHT-/');
INSERT INTO public.comparison_source_facts (comparison_summary_id, id, source_url) VALUES (5, 16, 'https://www.instagram.com/reel/DSxwISCDItJ/');
INSERT INTO public.comparison_source_facts (comparison_summary_id, id, source_url) VALUES (5, 17, 'https://www.instagram.com/p/DSx6ZhKiKK8/');
INSERT INTO public.comparison_source_facts (comparison_summary_id, id, source_url) VALUES (5, 18, 'https://www.youtube.com/watch?v=BJsyIThFaFU');

SELECT setval('comparison_source_facts_seq', (SELECT MAX(id) FROM public.comparison_source_facts));