def on_success(self, data) :
	print data ['text']
	with open('scratch1.json', 'ab') as outfile :
		json.dump(data, outfile, indent = 4)
	with open('scratch2.json', 'ab') as xoutfile :
		json.dump(data, xoutfile, indent = 4)
	return


def on_success(self, data) :
	if dt.datetime.now() > self.stop_time :
		raise Exception('Time expired')
	fileName = self.fileDirectory + 'Tweets_' + dt.datetime.now().strftime("%Y_%m_%d_%H") + '.txt'
	open(fileName, 'a').write(json.dumps(data) + '\n')

