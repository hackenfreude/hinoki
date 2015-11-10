to exercise:

1. run a repl
2. `(use 'ring.adapter.jetty)`
3. `(use 'hinoki.core)`
4. `(run-jetty first-handler {:port 11111 :join? false})`
5. `(run-jetty second-handler {:port 22222 :join? false})`
6. `(begin)`
7. this will produce a simulated log file with corrrelation ids at test.log.
8. run the awk script on it: `awk -f agg.awk test.log`

