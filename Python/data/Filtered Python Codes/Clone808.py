def strip_output(nb) :
	for ws in nb.worksheets :
		for cell in ws.cells :
			if hasattr(cell, "outputs") :
				cell.outputs = []
			if hasattr(cell, "prompt_number") :
				del cell ["prompt_number"]


def strip_output(nb) :
	for cell in nb.cells :
		if hasattr(cell, "outputs") :
			cell.outputs = []
		if hasattr(cell, "prompt_number") :
			del cell ["prompt_number"]

