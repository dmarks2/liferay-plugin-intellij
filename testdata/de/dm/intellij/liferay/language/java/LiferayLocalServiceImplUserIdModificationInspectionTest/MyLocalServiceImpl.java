public class MyLocalServiceImpl {

	public MyModel updateModel(long userId) {
		MyModel myModel = new MyModel();

		<warning descr="Modifying userId in update method may change entity ownership and permissions">myModel.setUserId(userId)</warning>;

		return myModel;
	}
}
