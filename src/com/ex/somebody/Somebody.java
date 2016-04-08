package com.ex.somebody;

/*
某人的所有属性
 * */

public class Somebody {
	//基本资料
	private int id;                //somebody ID号
	private String name;           //人名	
	private String sex;            //性别	
	private String birthday;       //出生日期
	private String nativePlace;    //籍贯
	
	//联系方式
	private String nickName;         //昵称
	private String telephone;      //联系电话,存在几个电话时，保存常用的一个电话，以下联系方法同此
	private String email;          //电子邮件
	private String qq;             //QQ
	private String weibo;          //微博
	private String boke;           //博客
	private String wechat;         //微信
	private String address;          //通信地址

	//个人情况
	private String[] needs;          //*个人需求*
	private float height;            //身高
	private float weight;            //体重
	private String bloodType;        //血型
	private String nationality;      //民族
	private String party;            //政治面貌
	private boolean married;         //是否已婚
	private boolean healthCondition; //健康状态
	private String occupation;       //职业
	private String workUnits;        //工作单位
	private String workTitle;        //工作职位
	private String income;           //收入状况
	private int characterType;       //性格类型,根据九型人格分类
	private String[] hobby;          //爱好	
	private String[] strongPoint;    //强项
	private String[] weakPoint;      //弱点
	
    //资源状况
	private int personalWealth;      //个人财富
	private int socialClass;         //社会地位
	private int educationalStatus;   //教育程度	
	private int cohesion;            //亲密度
	private String relationship;     //与“我”的关系

	//关联人物
	private String[][] relatedPerson;  //家庭成员、亲戚、领导、同事、同学、老乡、朋友、合伙人、其他人
	                                   //{{"人名1","人员编号1","与somebody关系1","somebody编号"},{},...}
	/*
	private String[] familyMember;   
	private String[] kinfolk;        //
	private String[] workmate;       //
	private String[] leader;         //
	private String[] schoolmate;     //同学
	private String[] fellowVillager; //
	private String[] friend;         //朋友
	private String[] cooperativePerson; //合伙人
	private String[] otherPerson;       //其它人	
	*/
	//其它资源挖掘
	private String[] loginName;    //常用登录用户名
	private String[] password;     //密码
	
	
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
