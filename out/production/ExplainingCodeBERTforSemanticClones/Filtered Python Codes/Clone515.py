def func() :
	sql = " select some rows "
	dbconn = "connect and open to dtabase code"
	n = 0
	ret = execute(sql, n)
	while ret is not None :
		yield ret
		n += 1
		ret = execute(sql, n)
	dbclose()


def func() :
	sql = "select some rows"
	dbconn = connect_and_open_database()
	cursor = dbconn.cursor()
	cursor.execute(sql)
	yield cursor_iter(cursor)
	dbclose()

