def getmonth(day, week, year) :
	for month in range(1, 13) :
		try :
			date = DT.datetime(year, month, day)
		except ValueError :
			continue
		iso_year, iso_weeknum, iso_weekday = date.isocalendar()
		if iso_weeknum == week :
			return date.month


def getmonth(day, week, year) :
	d = datetime.strptime('%s %s 1' % (week - 1, year), '%W %Y %w')
	for i in range(0, 7) :
		d2 = d + timedelta(days = i)
		if d2.day == day :
			return d2.month

