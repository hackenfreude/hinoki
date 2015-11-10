BEGIN {
	FS="\\||:";
}

{
	if (!uniques[$12]++) {
		spans[$12]++;
	}

	if ($10 == " ENTER ") {
		starts[$12 "|" $8]++;
	}
	
	if ($10 == " EXIT ") {
		ends[$12 "|" $8]++;
	}
	
	ancestors[$12 "|" $4]++;
	executors[$12 "|" $2]++;
	tracemaps[$12 "|" $6]++;
}

END {
	for (span in spans) {
		hops = -1;
		tracename = "?";
		starttime = "?";
		endtime = "?";
		executorname = "?";

		for (start in starts) {
			if (index(start, span) > 0) {
				split(start,startsplit,"|");
				starttime=startsplit[2];
			}
		}
		
		for (end in ends) {
			if (index (end, span) > 0) {
				split(end,endsplit,"|");
				endtime = endsplit[2];
			}
		}
		
		for (ancestor in ancestors) {
			if (index (ancestor, span) > 0) {
				hops++;
			}
		}
		
		for (executor in executors) {
			if (index (executor, span) > 0) {
				split(executor,executorsplit,"|");
				executorname = executorsplit[2];
			}
		}
		
		for (tracemap in tracemaps) {
			if (index (tracemap, span) > 0) {
				split(tracemap,tracemapsplit,"|");
				tracename = tracemapsplit[2];
			}
		}
		
		if (length (span) > 0) {
			print tracename span starttime endtime hops executorname;
		}
	}
}

