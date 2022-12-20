def addToInventory(inventory, addedItems) :
	for item in addedItems :
		inventory.setdefault(item, 0)
		inventory [item] = inventory [item] + 1
	return (inventory)


def addToInventory(inventory, addedItems) :
	for v in addedItems :
		if v in inventory.keys() :
			inventory [v] += 1
		else :
			inventory [v] = 1

