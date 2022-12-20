def standings(team) :
	for league, teams_dict in teams.items() :
		try :
			teams_dict [team]
			print (teams_dict [team], team)
			print (league)
			break
		except KeyError :
			continue


def standings(reply) :
	for league, single_dictionary in teams.items() :
		if reply in single_dictionary.values() :
			for key, value in single_dictionary.items() :
				if reply == value :
					print (key, value)
					print (league)
			break
	else :
		print ('Please Try Again')

