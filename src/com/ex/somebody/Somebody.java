package com.ex.somebody;

/*
ĳ�˵���������
 * */

public class Somebody {
	//��������
	private int id;                //somebody ID��
	private String name;           //����	
	private String sex;            //�Ա�	
	private String birthday;       //��������
	private String nativePlace;    //����
	
	//��ϵ��ʽ
	private String nickName;         //�ǳ�
	private String telephone;      //��ϵ�绰,���ڼ����绰ʱ�����泣�õ�һ���绰��������ϵ����ͬ��
	private String email;          //�����ʼ�
	private String qq;             //QQ
	private String weibo;          //΢��
	private String boke;           //����
	private String wechat;         //΢��
	private String address;          //ͨ�ŵ�ַ

	//�������
	private String[] needs;          //*��������*
	private float height;            //���
	private float weight;            //����
	private String bloodType;        //Ѫ��
	private String nationality;      //����
	private String party;            //������ò
	private boolean married;         //�Ƿ��ѻ�
	private boolean healthCondition; //����״̬
	private String occupation;       //ְҵ
	private String workUnits;        //������λ
	private String workTitle;        //����ְλ
	private String income;           //����״��
	private int characterType;       //�Ը�����,���ݾ����˸����
	private String[] hobby;          //����	
	private String[] strongPoint;    //ǿ��
	private String[] weakPoint;      //����
	
    //��Դ״��
	private int personalWealth;      //���˲Ƹ�
	private int socialClass;         //����λ
	private int educationalStatus;   //�����̶�	
	private int cohesion;            //���ܶ�
	private String relationship;     //�롰�ҡ��Ĺ�ϵ

	//��������
	private String[][] relatedPerson;  //��ͥ��Ա�����ݡ��쵼��ͬ�¡�ͬѧ�����硢���ѡ��ϻ��ˡ�������
	                                   //{{"����1","��Ա���1","��somebody��ϵ1","somebody���"},{},...}
	/*
	private String[] familyMember;   
	private String[] kinfolk;        //
	private String[] workmate;       //
	private String[] leader;         //
	private String[] schoolmate;     //ͬѧ
	private String[] fellowVillager; //
	private String[] friend;         //����
	private String[] cooperativePerson; //�ϻ���
	private String[] otherPerson;       //������	
	*/
	//������Դ�ھ�
	private String[] loginName;    //���õ�¼�û���
	private String[] password;     //����
	
	
	public Somebody(){
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getBirthday() {
		return birthday;
	}


	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public String getNativePlace() {
		return nativePlace;
	}


	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getQq() {
		return qq;
	}


	public void setQq(String qq) {
		this.qq = qq;
	}


	public String getWeibo() {
		return weibo;
	}


	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}


	public String getBoke() {
		return boke;
	}


	public void setBoke(String boke) {
		this.boke = boke;
	}


	public String getWechat() {
		return wechat;
	}


	public void setWechat(String wechat) {
		this.wechat = wechat;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String[] getNeeds() {
		return needs;
	}


	public void setNeeds(String[] needs) {
		this.needs = needs;
	}


	public float getHeight() {
		return height;
	}


	public void setHeight(float height) {
		this.height = height;
	}


	public float getWeight() {
		return weight;
	}


	public void setWeight(float weight) {
		this.weight = weight;
	}


	public String getBloodType() {
		return bloodType;
	}


	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public String getParty() {
		return party;
	}


	public void setParty(String party) {
		this.party = party;
	}


	public boolean isMarried() {
		return married;
	}


	public void setMarried(boolean married) {
		this.married = married;
	}


	public boolean isHealthCondition() {
		return healthCondition;
	}


	public void setHealthCondition(boolean healthCondition) {
		this.healthCondition = healthCondition;
	}


	public String getOccupation() {
		return occupation;
	}


	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}


	public String getWorkUnits() {
		return workUnits;
	}


	public void setWorkUnits(String workUnits) {
		this.workUnits = workUnits;
	}


	public String getWorkTitle() {
		return workTitle;
	}


	public void setWorkTitle(String workTitle) {
		this.workTitle = workTitle;
	}


	public String getIncome() {
		return income;
	}


	public void setIncome(String income) {
		this.income = income;
	}


	public int getCharacterType() {
		return characterType;
	}


	public void setCharacterType(int characterType) {
		this.characterType = characterType;
	}


	public String[] getHobby() {
		return hobby;
	}


	public void setHobby(String[] hobby) {
		this.hobby = hobby;
	}


	public String[] getStrongPoint() {
		return strongPoint;
	}


	public void setStrongPoint(String[] strongPoint) {
		this.strongPoint = strongPoint;
	}


	public String[] getWeakPoint() {
		return weakPoint;
	}


	public void setWeakPoint(String[] weakPoint) {
		this.weakPoint = weakPoint;
	}


	public int getPersonalWealth() {
		return personalWealth;
	}


	public void setPersonalWealth(int personalWealth) {
		this.personalWealth = personalWealth;
	}


	public int getSocialClass() {
		return socialClass;
	}


	public void setSocialClass(int socialClass) {
		this.socialClass = socialClass;
	}


	public int getEducationalStatus() {
		return educationalStatus;
	}


	public void setEducationalStatus(int educationalStatus) {
		this.educationalStatus = educationalStatus;
	}


	public int getCohesion() {
		return cohesion;
	}


	public void setCohesion(int cohesion) {
		this.cohesion = cohesion;
	}


	public String getRelationship() {
		return relationship;
	}


	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}


	public String[][] getRelatedPerson() {
		return relatedPerson;
	}


	public void setRelatedPerson(String[][] relatedPerson) {
		this.relatedPerson = relatedPerson;
	}


	public String[] getLoginName() {
		return loginName;
	}


	public void setLoginName(String[] loginName) {
		this.loginName = loginName;
	}


	public String[] getPassword() {
		return password;
	}


	public void setPassword(String[] password) {
		this.password = password;
	}
}
