🔧 로직 설명
/src/main/java/linux/assignment/service/MainServiceImpl.java

📤 public InfoListResponseDto getInfoList()
프론트엔드에 데이터를 전달하기 위한 메서드
→ 리포지토리에서 불러온 값을 InfoListResponseDto의 static 메서드 set을 사용하여 Dto를 생성해서 반환

🔄 public void updateInfo()
xml 파일을 업데이트하기 위한 메서드
→ 가장 처음 DB에서 모든 매매 데이터를 가져온 데이터들을 joinItemToStr(it)을 사용하여 "위도|경도|매매가|..." 식으로 문자열로 Set에 저장,
xml을 파싱해서 같은 String 형식으로 Set에 add 시도, false가 반환되면 중복데이터라 판단하여 DB에 추가 안함.

→ 공공데이터포털에서 받아온 값은 자연어 주소기에, 위도-경도를 얻을 필요가 있었기에
우선 Map<해당주소, (위도, 경도)> cache에 해당 주소가 있는지 확인 후 없으면
DB의 저장된 데이터 중에 해당 주소를 가지고 있는지 확인, 있으면 cache에 저장.
없으면 NaverGeocodingService.java를 사용하여 한글주소 → 위도/경도로 변환, cache에 저장.

모든 업데이트 처리가 끝난 후에 해당 xml 파일 제거.
