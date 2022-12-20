def update(self, instance, validated_data) :
	user_data = validated_data.pop('user', {})
	user_serializer = UserSerializer(instance.user, data = user_data, partial = True)
	user_serializer.is_valid(raise_exception = True)
	user_serializer.update(instance.user, user_data)
	super(ProfileSerializer, self).update(instance, validated_data)
	return instance


def update(self, instance, validated_data) :
	user_data = validated_data.pop('user', None)
	for attr, value in user_data.items() :
		setattr(instance.user, attr, value)
	for attr, value in validated_data.items() :
		setattr(instance, attr, value)
	instance.save()
	return instance

