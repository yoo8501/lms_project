# lms_project
도서관리프로그램
	<ul>
		<h2>개발환경</h2>
		<li>oralce DB</li>
		<li>javaSE</li>
	</ul>
	<table border="1px;">
		<tr>
			<th>ui</th>
			<th>설명</th>
		</tr>
		<tr>
			<td><img
				src="https://postfiles.pstatic.net/MjAxOTA1MTNfMzYg/MDAxNTU3Njc3NzE4MjM1.x3BB4UxlhYGVkjJi_RflTzojBlMavZFyloNh09yXuPog.VuGcC-EikL52UEAhEl2u4Z-nEt8ReVxntddBjhnXh_kg.PNG.qwea8501/lms_db.png?type=w773" /></td>
			<td>
				<ul>
					<li>Connection 객체를 싱글턴 패턴으로 생성할 수 있게 클래스 정의</li>
					<li>바인드 변수 사용</li>
				</ul>
			</td>
		</tr>
		<tr>
			<td><img
				src="https://postfiles.pstatic.net/MjAxOTA1MTNfMjY5/MDAxNTU3Njc3NzE4MjMx.-p7p6rbMUMFiY4-l6tMazO2QdkKjiJP4UYiLtoDiMCcg.YvzS0gwENO_sjTTJM2c_0He6-YABosf8vhodhOdB2qEg.PNG.qwea8501/lms_%EB%A1%9C%EA%B7%B8%EC%9D%B8%EC%B0%BD.png?type=w773" />
				<img
				src="https://postfiles.pstatic.net/MjAxOTA1MTNfMjAz/MDAxNTU3Njc3NzE4NTAz._aSDBgCLQwJ6NdrKowgmPIfRKVCvPdAtw_sU9Z1eRegg.I-zNwynwEmYL2ECVUXDOenYwwT7tML5Gbmk0_kF-awUg.PNG.qwea8501/lms_%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85.png?type=w773" />
			</td>
			<td>
				<ul>
					<li>회원가입시 member table에 저장</li>
					<li>id중복체크하여 중복된 id 가입불가</li>
					<li>로그인시 해당GUI 사라지며 클라이언트 GUI로 이동</li>
				</ul>
			</td>
		</tr>
		<tr>
			<td><img
				src="https://postfiles.pstatic.net/MjAxOTA1MTNfMjg3/MDAxNTU3Njc3NzE4NDQ0.a54KJaoPTQ9teqEbXhujIUqTpLJvC_rgMwMlCK-bldgg.29uZj9iiwk1C8wyEOk0Jf2HZnPowPu5rmJ5YcvDANMIg.PNG.qwea8501/lms_%ED%9A%8C%EC%9B%90%EA%B4%80%EB%A6%AC.png?type=w773" /></td>
			<td>
				<ul>
					<li>member의 권한이 관리자가 아닌 회원관리</li>
				</ul>
			</td>
		</tr>
				<tr>
			<td><img
				src="https://postfiles.pstatic.net/MjAxOTA1MTNfMTEw/MDAxNTU3Njc3NzE4MjMz.teIUgrAWQkmIL2PFknfDiEBu3__dHoxRKMSyQIYyd3Mg.sPqHw5IAdfXW4O5WL3x0U0CYseH3tKawLfj8tegzq98g.PNG.qwea8501/lms_%EB%8F%84%EC%84%9C%EB%AA%A9%EB%A1%9D.png?type=w773" /></td>
			<td>
				<ul>
					<li>전체 도서 button 클릭시 유실되지않은 모든 도서 가져오기</li>
					<li>관내도서 클릭시 대출중이지 않은 도서 가져오기</li>
					<li>대출도서 클릭시 대출 중인 도서 가져오기</li>
					<li>목록 클릭시 textarea에 자동으로 채워지며 도서번호는 수정불가</li>
				</ul>
			</td>
		</tr>
			<tr>
			<td><img
				src="https://postfiles.pstatic.net/MjAxOTA1MTNfMjQz/MDAxNTU3Njc3NzE4NTMz.9kkqW58GjLkB3vrLWTlBgdwhxQA0WjpzSOWkyt9qAGgg.DtYg8yCyPU9e8SnHg2dvx4oSFP_fC1nu_8r6izxIQ5Mg.PNG.qwea8501/lms_%EC%9C%A0%EC%8B%A4%EB%AA%A9%EB%A1%9D.png?type=w773" /></td>
			<td>
				<ul>
					<li>유실 및 파손 도서만 가져오기</li>
				</ul>
			</td>
		</tr>
			<tr>
			<td><img
				src="https://postfiles.pstatic.net/MjAxOTA1MTNfMTkz/MDAxNTU3NjkwMzc1ODMw.EI1LETzO-ZnMbU2WICRjzylGKo01_NBRX995x1-ixtgg.U29FqU-aO6lLaVPO1o3eorxUcbjU7xRt3bcjx2lOUeAg.PNG.qwea8501/%EB%8F%84%EC%84%9C%EC%9A%94%EC%B2%AD.png?type=w773"/>
			<img
				src="https://postfiles.pstatic.net/MjAxOTA1MTNfMjIw/MDAxNTU3Njc3NzE4NDE5.ULDyRu3xu1xHP0Kp9nlYTZSy9UcT4TIL75dZH073wjkg.9A3yBZV6cjIBzXz4X6HBPgQoc7J0PrL1_7ukdbuFYJsg.PNG.qwea8501/lms_%EC%9A%94%EC%B2%AD%EB%8F%84%EC%84%9C.png?type=w773"/></td>
			<td>
				<ul>
					<li>유저가 도서를 요청을하면 관리자GUI에서 목록 추가</li>
				</ul>
			</td>
		</tr>
					<tr>
			<td><img
				src="https://postfiles.pstatic.net/MjAxOTA1MTNfOTEg/MDAxNTU3Njc3NzE4MjMw.rMfXrbKnnAOMi5KkYY1HfneoawNXue-vd6yggCgfB6kg.tEuC7ZMzxhTjkcW6waeA6FY-QUjgqmpmz5nBWQ7gU8Yg.PNG.qwea8501/lms_%EA%B5%AC%EB%A7%A4%EB%8F%84%EC%84%9C.png?type=w773"/>
			</td>
			<td>
				<ul>
					<li>기간을 월별단위로 검색하여 구입한 책의 목록을 가져오거나 추가한다</li>
				</ul>
			</td>
		</tr>
	</table>
