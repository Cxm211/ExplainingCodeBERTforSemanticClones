def __init__(self, ev_list = None) :
	self._trigger = Event()
	if ev_list :
		self._t_list = [
		Thread(target = self._triggerer, args = (ev,)) for ev in ev_list
		]
	else :
		self._t_list = []


def __init__(self, events_list, condition) :
	_Event.__init__(self)
	self.event_list = events_list
	self.condition = condition
	for e in events_list :
		self._setup(e, self._state_changed)
	self._state_changed()

