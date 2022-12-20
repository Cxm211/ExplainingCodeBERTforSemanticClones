def previous_quarter(date) :
	date = DT.datetime(date.year, date.month, date.day)
	rr = rrule.rrule(
	rrule.DAILY,
	bymonth = (3, 6, 9, 12),
	bymonthday = - 1,
	dtstart = date - DT.timedelta(days = 100))
	result = rr.before(date, inc = False)
	return result.date()


def previous_quarter(ref) :
	if ref.month < 4 :
		return datetime.date(ref.year - 1, 12, 31)
	elif ref.month < 7 :
		return datetime.date(ref.year, 3, 31)
	elif ref.month < 10 :
		return datetime.date(ref.year, 6, 30)
	return datetime.date(ref.year, 9, 30)

