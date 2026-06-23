/**
 * board/detail.jsp => boardDetail_comment.js
 */

console.log("boardDetail_comment.js in");
console.log(bno);

document.getElementById('cmtAddBtn').addEventListener('click', ()=>{
    const cmtWriter = document.getElementById('cmtWriter');
    const cmtText = document.getElementById('cmtText');
	
	if(cmtWriter.value.trim() == "" || cmtText.value.trim() == ""){
		alert('댓글의 내용을 입력해주세요.');
		return;
	}
	
    // 댓글 객체 생성
    const cmtData={
        bno: bno,
        writer : cmtWriter.value,
        contents : cmtText.value
    }

    console.log(cmtData);

    cmtText.value = '';
    cmtText.focus();

    postCommentToServer(cmtData).then(result =>{
        console.log(result);
        if(result == '1'){
            alert('댓글등록 성공!!!');
        }else{
            alert('댓글등록 실패!');
        }

        // 댓글 리스트를 띄우기
        printCommentList(bno);
        
    })
});

// 데이터 전송 함수 (post)
async function postCommentToServer(cmtData) {
    try {
        // post 데이터를 전송할 때 
        // url, headers(content-type), body(cmtData)
        const url = "/cmt/post";
        const config = {
            method: 'post',
            headers:{
                'content-type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtData)
        }

        // 전송
        const response = await fetch(url, config);
        const result = await response.text();  //isOk
        return result;

    } catch (error) {
        console.log(error);
    }
}

// 리스트 호출
async function getCommentListFromServer(bno) {
    try {
        const response = await fetch(`/cmt/list?bno=${bno}`);
        const result = await response.json(); // 댓글 리스트 [{},{},{}]
        return result;
    } catch (error) {
        console.log(error);
    }
}

function printCommentList(bno){
    getCommentListFromServer(bno).then(result =>{
        console.log(result);
        const div = document.getElementById('commentLine');
        let str='';
        if(result.length > 0){
            //댓글이 있는 경우
            for(let cmt of result){
                str+=`<div>`;
                str+=`<div>${cmt.cno} / ${cmt.writer} (${cmt.regdate})</div>`;
                str+=`<div>`;
                str+=`<input type="text" class="cmtText" value="${cmt.contents}">`;
                str+=`<button type="button" class="mod" data-cno=${cmt.cno}>❗</button>`;
                str+=`<button type="button" class="del" data-cno=${cmt.cno}>❌</button>`;
                str+=`</div></div>`;
            }
            div.innerHTML = str;
        }else{
            // 댓글이 없는 경우
            div.innerHTML = `<div>댓글이 없습니다.</div>`;
        }
    })
}

document.getElementById('commentLine').addEventListener('click',(e)=>{
    
	if(e.target.classList.contains('mod')){
		// 수정 => 수정된 cno, contents 객체를 만들어서 전송
        let cno = e.target.dataset.cno;
        // closest : 내 타겟을 기준으로 나랑 가장 가까운 부모의 css 선택자를 찾기
        let div = e.target.closest('div');
        console.log(div);
		let cmtText = div.querySelector(".cmtText").value;

        let cmtData = {
            cno: cno,
            contents: cmtText
        }
        console.log(cmtData);
        // 비동기 전송 함수 호출
        updateCommentToServer(cmtData).then(result =>{
            if(result > 0){
                alert("댓글 수정 성공!")
            }else{
                alert("댓글 수정 실패!")
            }

            printCommentList(bno);
        })

	}

    if(e.target.classList.contains('del')){
        // 삭제 => cno만 인지하여 삭제요청
        let cno = e.target.dataset.cno;
		
		deleteCommentToServer(cno).then(result =>{
			if(result > 0){
                alert("댓글 삭제 성공!")
            }else{
                alert("댓글 삭제 실패!")
            }
            printCommentList(bno);
		})
    }
})

// 삭제 데이터 전송 요청
async function deleteCommentToServer(cno){
	try{
		const response = await fetch(`/cmt/delete?cno=${cno}`);
        const result = await response.text();
        return result;
	}catch(error){
		console.log(error);
	}
}


// 수정 데이터 전송 요청
async function updateCommentToServer(cmtData) {
    try {
        const url = "/cmt/modify";
        const config = {
            method:'post',
            headers:{
                'content-type':'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtData)
        }

        const response = await fetch(url, config);
        const result = await response.text();
        return result;
        
    } catch (error) {
        console.log(error);
    }
}
