INSERT INTO public.sentiment_example (id, sentiment_summary_id, sentiment) VALUES (1, 1, 'NEGATIVE');
INSERT INTO public.sentiment_example (id, sentiment_summary_id, sentiment) VALUES (2, 1, 'ALARMIST');
INSERT INTO public.sentiment_example (id, sentiment_summary_id, sentiment) VALUES (3, 1, 'IRONIC');
INSERT INTO public.sentiment_example (id, sentiment_summary_id, sentiment) VALUES (4, 1, 'PERSUASIVE');
INSERT INTO public.sentiment_example (id, sentiment_summary_id, sentiment) VALUES (5, 2, 'POSITIVE');
INSERT INTO public.sentiment_example (id, sentiment_summary_id, sentiment) VALUES (6, 2, 'PERSUASIVE');
INSERT INTO public.sentiment_example (id, sentiment_summary_id, sentiment) VALUES (13, 5, 'POSITIVE');
INSERT INTO public.sentiment_example (id, sentiment_summary_id, sentiment) VALUES (14, 5, 'AGGRESSIVE');
INSERT INTO public.sentiment_example (id, sentiment_summary_id, sentiment) VALUES (15, 5, 'IRONIC');

SELECT setval('sentiment_example_seq', (SELECT MAX(id) FROM public.sentiment_example));