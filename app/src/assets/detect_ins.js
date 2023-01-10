console.log("加载ins解析代码>>>>>>>>>")

// 状态最大检测次数
var STATUS_MAX_TIMES = 20
// 无反应尝试再次点击
var CHECK_MAX_TIMES = 3

var userProfile = null;
// 点击冷却
var isClickCooling = true
var collectRootNode = null;
// 当前下标
var nowIndex = 0;
// 本次采集的长度
var collectLength = 0;
// 标记为开始采集
var isStartCollect = false;
// 指示器element
var indicatorArrays = null;
// 延时Id
var timeoutId = -1;
// 当前步骤
var stepIndex = 0;
// 去重检测
var duplicateCheck = null;
// 检测次数
var checkTimes = 0;
// 遮罩
var coverImg = null;
// 点击时间戳
var lastStamp = 0;
var leftClassName = null
var rightClassName = null

function dumpHtml(content) { }

function warn(tag, msg = "") {
	ADAPTATION_HOLDER.warn(tag, msg);
}

function receiveJsParseResult(jsonResult) {
	ADAPTATION_HOLDER.receiveJsParseResult(jsonResult);
}

function startReceiveData(userProfile, collectNum, isStory) {
	ADAPTATION_HOLDER.startReceiveData(JSON.stringify(userProfile), collectNum, isStory);
}

function userDescribeChange(userProfile) {
	ADAPTATION_HOLDER.userDescribeChange(JSON.stringify(userProfile));
}

function sendBlobData(thumbnailUrl, sorceUrl) {
	ADAPTATION_HOLDER.sendBlobData(thumbnailUrl, sorceUrl);
}

function sendDataJson(dataJson,length) {
	ADAPTATION_HOLDER.sendDataJson(dataJson,length);
}

function reportError(msg) {
	ADAPTATION_HOLDER.reportError(msg);
}

function endReceiveData() {
	ADAPTATION_HOLDER.endReceiveData();
}

function checkClipboard() {
	ADAPTATION_HOLDER.checkClipboard();
}

function download(){
     var buttonParentNode = document.getElementsByClassName("ins_dl");
     var button = buttonParentNode[buttonParentNode.length-1]
     if(buttonParentNode.length>0) {
        var button = buttonParentNode[buttonParentNode.length-1]
        if(button != null) {
           button.click();
        }
        return true;
     }
     else{
        return false;
     }
}

var createBtn = function (top) {
	var button = document.createElement("DIV")
	button.className = "ins_dl";
	button.setAttribute("style", "position:absolute;top:" + top +
		";left:0px;width: 60px; height: 60px; z-index:9999 ");
	button.innerHTML =
		'<img style="position:absolute;top:0px;bottom:0px;left:0px;right:0px;margin:auto;;width: 60px; height: 60px;pointer-events: none" src="data:image/png;base64,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" />';
	return button;
}

var tagMedia = function (rootNode, ele, parentNode, isStory) {
	var tagName = ele.tagName
	var targetContainer = parentNode
	//已经注入的标签无须再次处理
	console.log("videos class tagMedia---" + ele.getAttribute('class') )
	if (targetContainer.getAttribute("tag") == 1) {
		//return;
	}
	var top = "0px"
	if (isStory) {
		top = "120px"
	}
	var button = createBtn(top)
	//注入下载按钮
	button.onclick = function (event) {
		if (!isClickCooling) {
			return
		}
		isClickCooling = false
		setTimeout(function () {
			isClickCooling = true;
		}, 1500);
		event.stopPropagation();
		resetStatus(); // 重置状态
        describeMoreClick(rootNode)
		setTimeout(function () {
		// 采集用户信息
		if (isStory) {
			userProfile = findUserProfileInStory(rootNode)
		} else {
			userProfile = findUserProfile(rootNode)
		}
		// 判断是否采集当前article
		var dataTags = rootNode.getElementsByTagName("li");
		if (dataTags.length > 0) { // 需要收集所有图片
			var length = collectImgs(rootNode, userProfile)
		} else { // 单视频/图片处理
			// add 0217 解决部分手机事件透传失效问题
			if (event.target.className != "ins_dl") {
				clickTarget = event.target.parentNode.parentNode
			} else {
				clickTarget = event.target.parentNode
			}
			// 单视频检测
			var videos = clickTarget.getElementsByTagName("video")

			if (videos.length > 0) {
				var videoUrl = videos[0].src;
				if (!videoUrl) {
					var sources = videos[0].getElementsByTagName("source")
					if (sources.length > 0) {
						videoUrl = sources[0].src
					}
				}
				console.log("单视频检测 videoUrl: " + videoUrl);
				var thumbnailUrl = videos[0].poster
				if (thumbnailUrl && thumbnailUrl.indexOf("base64") >= 0) {
					//story视频
					var imgs = clickTarget.getElementsByTagName("IMG")
					var thumbnailUrl = ""
					if (imgs.length > 0) {
						thumbnailUrl = imgs[0].src
					}
				}
				console.log("单视频检测 thumbnailUrl: --- " + thumbnailUrl);
				if (!isEmpty(videoUrl)) {
					//startReceiveData(userProfile, 1, isStory);
					if (videoUrl.startsWith("blob")) { // blob视频，提供原始地址信息，以原有逻辑下载
						var sourceUrl = findSourceUrl(rootNode);
						console.log("单视频检测 sourceUrl: --- " + sourceUrl);
					} else {
						var videoData = {
							displayUrl: thumbnailUrl,
							videoUrl: videoUrl,
							userProfile: userProfile
						}
						sendDataJson(JSON.stringify(videoData),1)
					}
					//endReceiveData()
				} else {
					warn("INS_VIDEO_URL_INVALID", videoUrl);
				}
			} else { // 单图片检测
                console.log("单图片检测1");
                var imgs = clickTarget.getElementsByTagName("img");
                var imgUrl = null;
                if (imgs.length > 0) {
                    if (isEmpty(imgs[0].className) && imgs.length > 1) {
                        imgUrl = imgs[1].src
                    } else {
                        imgUrl = imgs[0].src
                    }
                }
                console.log("单图片检测2: " + imgUrl);
                if (!isEmpty(imgUrl)) {
                    var imgData = {
                        displayUrl: imgUrl,
                        videoUrl: "",
                        userProfile: userProfile
                    }
                    console.log("单图片检测3");
                    //startReceiveData(userProfile, 1, isStory);
                    sendDataJson(JSON.stringify(imgData),1)
                    //endReceiveData()
                } else {
                    warn("INS_IMG_URL_INVALID", imgUrl);
                }
            }
		}
		}, 100);
	}
	targetContainer.appendChild(button);
	targetContainer.setAttribute("tag", 1)
}

function findUserProfile(rootNode) {
	var titleParent = rootNode.getElementsByTagName("header")[0]
	var title = ""
	if (titleParent != null) {
		var titleE = titleParent.getElementsByTagName("a")[0]
		if (titleE != null) {
			title = titleE.innerText;
		}
	}

	// 用户信息root标签
	var userEle = rootNode.getElementsByTagName("header")[0]
	var headUrl = ""
	var userName = ""
	var describe = ""
	if (!isEmpty(userEle)) {
		// 头像
		var headEle = userEle.getElementsByTagName("img")[0]
		if (!isEmpty(userEle)) {
			headUrl = headEle.src
		}
		// 用户名
		var usernameEle = userEle.children[1].getElementsByTagName("a")[0]
		if (!isEmpty(usernameEle)) {
			userName = usernameEle.innerText
		}
	}

    // 描述
    var commentRoot = rootNode.children[2]
    if (commentRoot != null) {
        var selectedE = commentRoot.getElementsByTagName("section")[0]
        if (selectedE != null) {
            var selectedParentE = selectedE.parentNode
            var describeParentE = selectedParentE.children[2]
            if (describeParentE != null) {
                var nameE = describeParentE.getElementsByTagName("a")[0]
                if (nameE != null) {
                    describe = nameE.parentNode.innerText
                }
            }
        }
    }

    return {
        "headUrl": headUrl,
        "userName": userName,
        "describe": describe,
    }
}

function describeMoreClick(rootNode) {
    // 描述
    var commentRoot = rootNode.children[2]
    if (commentRoot != null) {
        var selectedE = commentRoot.getElementsByTagName("section")[0]
        if (selectedE != null) {
            var selectedParentE = selectedE.parentNode
            var describeParentE = selectedParentE.children[2]
            if (describeParentE != null) {
                var nameE = describeParentE.getElementsByTagName("a")[0]
                if (nameE != null) {
                    var describeMore = rootNode.getElementsByClassName("_aacl _aaco _aacu _aacy _aad6 _aade")[0]
                    describeMore.click()
                }
            }
        }
    }
}

function sleep(d){
  for(var t = Date.now();Date.now() - t <= d;);
}

// 在Story中寻找用户信息
function findUserProfileInStory(rootNode) {
	// 用户信息root标签
	var headerE = rootNode.getElementsByTagName("header")[0]
	var headUrl = ""
	var userName = ""
	var describe = ""
	if (headerE != null) {
		// 头像
		var headEle = headerE.getElementsByTagName("img")[0]
		if (headEle != null) {
			headUrl = headEle.src
		}
		// 用户名
		var timeE = headerE.getElementsByTagName("time")[0]
		if (timeE != null) {
			var usernameEle = timeE.parentNode.parentNode.getElementsByTagName("a")[0];
			if (usernameEle != null) {
				userName = usernameEle.innerText
			}
		}
	}
	return {
		"headUrl": headUrl,
		"userName": userName,
		"describe": describe,
	}
}

function findSourceUrl(rootNode) {
	var timeE = rootNode.getElementsByTagName("time")[0]
	if (timeE != null) {
		return timeE.parentNode.parentNode.href;
	}
	return ""
}

function tagRealVideo(videoSrc, videoParent, videoInfoPPE) {
    console.log("tagRealVideo E");
	var targetContainer = videoParent
	//已经注入的标签无须再次处理
	if (isEmpty(videoSrc) || targetContainer.getAttribute("tag") == 1) {
		return;
	}
	var button = createBtn("0px")
	//注入下载按钮
	button.onclick = function (event) {
		if (!isClickCooling) {
			return
		}
		isClickCooling = false
		setTimeout(function () {
			isClickCooling = true;
		}, 1500);
		event.stopPropagation();
		// 采集用户信息
		var videoUrl = videoSrc;
		var headUrl = "";
		var userName = "";
		var describe = "";
		console.log("detect_ins videoUrl: " + videoUrl)
		// 用户信息
		var infoPPE = videoInfoPPE.children[1]
		if (infoPPE != null) {
			var infoPE = infoPPE.children[0]
			if (infoPE != null) {
				var infoE = infoPE.children[1] // 用户头像/username/describe所有信息
				if (infoE != null) {
					var userInfoPPPE = infoE.children[0]
					if (userInfoPPPE != null) {
						var headE = userInfoPPPE.getElementsByTagName("img")[0]
						headUrl = headE.src;
						var userInfoPPE = userInfoPPPE.children[0]
						if (userInfoPPE != null) { // a标签
							var userInfoPE = userInfoPPE.children[0]
							if (userInfoPE != null) {
								var userNameE = userInfoPE.children[1]
								if (userNameE != null) {
									userName = userNameE.textContent;
								}
							}
						}
					}
					var describeE = infoE.children[1]
					if (describeE != null) {
						describe = describeE.innerText
					}
				}
			}
		}
		var userProfile = {
			"headUrl": headUrl,
			"userName": userName,
			"describe": describe,
		}
        startReceiveData(userProfile, 1, false);
        var videoData = {
        	videoUrl: videoUrl
        }
        sendDataJson(JSON.stringify(videoData),1)
        endReceiveData()
	}
	targetContainer.appendChild(button);
	targetContainer.setAttribute("tag", 1)
}


//获取所有的img标签
var findAllMedias = function (target, from) {
	var currUrl = window.location.href;

	if (/https?:\/\/.*instagram\.com\/stories\.*\//.test(currUrl)) {
		//story页面 查找所有的section标签
		if (target.className == "section") {
			handleSection(target)
		} else {
			var sections = target.getElementsByTagName("section")
			if (sections.length > 0) {
				for (var i = 0; i < sections.length; i++) {
					handleSection(sections[i])
				}
			}
		}
	} else if (/https?:\/\/.*instagram\.com\/reels\/videos\.*\//.test(currUrl)) {
		var mainE = document.getElementsByTagName("main")[0]
		if (mainE != null) {
			var mainChildOne = mainE.children[0]
			if (mainChildOne != null) {
				var listParent = mainChildOne.children[0]
				if (listParent != null) {
					var list = listParent.childNodes
					for (var i = 0; i < list.length; i++) {
						var mediaItemE = list[i];
						var videoInfoPPE = mediaItemE.children[0]
						if (videoInfoPPE != null) {
							var videoE = videoInfoPPE.getElementsByTagName("video")[0] // 视频信息
							if(videoE != null){
							    tagRealVideo(videoE.src, mediaItemE, videoInfoPPE)
							}
                        }
					}
				}
			}
		}
	} else {
		//找到所有的article标签
		if (target.className == "article") {
			handleArticle(target)
		} else {
			var articles = target.getElementsByTagName("article")
			if (articles.length > 0) {
				var hasMedia = true;
				if (articles.length > 4) {
					hasMedia = false; // 需要检测
				}
				for (var i = 0; i < articles.length; i++) {
					var isValid = handleArticle(articles[i])
					if (isValid) {
						hasMedia = true;
					}
				}
				// if (!hasMedia) { // 上报异常
				// 	reportError("has article no photo/video");
				// }
			}
		}
	}
}

var handleArticle = function (article) {
    console.log("handleArticle E");
	var isValid = false
	var ulE = article.getElementsByTagName("ul")[0]
	if (ulE != null) {
		var liEs = article.getElementsByTagName("li")
		if (liEs.length > 0) {
			for (var idx = 0; idx < liEs.length; idx++) {
				var liE = liEs[idx]
				var imgE = liE.getElementsByTagName("img")[0]
				if (imgE != null) {
					isValid = true
					tagMedia(article.children[0], imgE, liE, false)
				} else {
					var videoE = liE.getElementsByTagName("video")[0]
					if (videoE != null) {
						isValid = true
						tagMedia(article.children[0], videoE, liE, false)
					}
				}
			}
		}
		//		var contentRoot = article.children[0].children[1]
		//		if (contentRoot != null) {
		//			var contentParent = contentRoot.children[0];
		//			if (contentParent != null) {
		//				simulateTouch(contentParent);
		//			}
		//		}
	} else {
		var rootNode = article.children[0]
		if (rootNode != null) {
			var contentRoot = rootNode.children[1]
			if (contentRoot != null) {
				var imgs = contentRoot.getElementsByTagName("img")
				var imgE = imgs[0]
				if (imgs.length == 1 && imgE.parentNode.className != "ins_dl") {
					tagMedia(rootNode, imgs[0], imgs[0].parentNode, false)
				} else {
					var videos = contentRoot.getElementsByTagName("video")
					if (videos.length == 1) {
						console.log("videos class handleArticle---" + videos[0].getAttribute('class') )
						tagMedia(rootNode, videos[0], videos[0].parentNode, false)
					}
				}
			}
		}
	}
	console.log("handleArticle X");
	return isValid;
}

var handleSection = function (section) {
    var videoE = section.getElementsByTagName("video")[0];
    if (videoE != null) {
        tagMedia(section, videoE, videoE.parentNode, true)
    } else {
        var imgE = section.getElementsByTagName("img")[0];
        if (imgE != null) {
            tagMedia(section, imgE, imgE.parentNode, true)
        }
    }
}

/**
 * 获取图片数组
 */
function collectImgs(rootNode, userProfile, isStory) {
	if (collectLength != 0) {
		return;
	}
	collectRootNode = rootNode;
	collectLength = 1
	// 总长度通过指示器长度确定
	indicatorArrays = getIndicatorArray(rootNode)
	if (indicatorArrays != null) {
		collectLength = indicatorArrays.children.length;
	}
	console.log("indicatorArrays" + indicatorArrays)
	// 当前下标
	nowIndex = getNowIndex(indicatorArrays);
	// 确保切换到第一页
	startReceiveData(userProfile, collectLength, isStory);
	return collectLength
}

/**
 * 由Android端调用
 */
function startCollectData() {
	stepIndex = 1;
	isStartCollect = true;
	checkStep();
}

/**
 * 步骤检测
 */
function checkStep() {
	console.log("collectLength: " + collectLength);
	if (collectLength == 0) {
		return;
	}
	cancelTimeout();
	if (stepIndex == 1) { // 切换到第一页
		checkToLeft();
	} else if (stepIndex == 2) { // 开始搜集
		collectImg();
	} else if (stepIndex == 3) { // 复原
		restoreIndex();
	} else if (stepIndex == 4) { // 上报结束
		reportEndReceive();
	} else { // 结束
		resetStatus();
		console.log("complete !!!");
	}
}

function resetStatus() {
	collectRootNode = null;
	nowIndex = 0;
	collectLength = 0;
	indicatorArrays = null;
	timeoutId = -1;
	stepIndex = 0;
	duplicateCheck = null;
	// 检测次数
	checkTimes = 0;
}

/**
 * 第一步，切换到第一页
 */
function checkToLeft() {
	console.log("checkToLeft enter nowIndex: " + nowIndex);
	if (nowIndex != 0) { // 开始不是第一页
		var index = getNowIndex(indicatorArrays);
		console.log("checkToLeft index: " + index);
		if (index > 0) {
			// 触发左点击，最多等 @STATUS_INTERVAL_TIME 时间
			for (var i = 0; i <= index; i++) {
				leftBtnClick(collectRootNode, 0);
			}
			checkTimes = 0;
			timeoutCheck(50);
			return
		}
	}
	// 进入下一步
	stepIndex = 2;
	checkTimes = 0;
	checkStep();
}

/**
 * 第二步，收集图片
 */
function collectImg() {
	if (duplicateCheck == null) { // 初始化
		duplicateCheck = new Set();
	}
	// 搜集当前下标
	var index = getNowIndex(indicatorArrays);
	var ul = collectRootNode.getElementsByTagName("ul")[0];
	var dataTags = ul.getElementsByTagName("li");
	var length = dataTags.length;
	console.log("collectImg 当前下标:" + (index+1) + "--- li length---" + length)
	var dataSize = duplicateCheck.size;
	var hasEmpty = false;

	for (var i = 0; i < length; i++) {
		var videoData = getVideoUrl(dataTags[i])
		if (videoData != null && !duplicateCheck.has(videoData.videoUrl)) {
			duplicateCheck.add(videoData.videoUrl)
			sendDataJson(JSON.stringify(videoData),collectLength)
		} else {
			var imgData = getImagUrl(dataTags[i]);
			if (imgData != null && !duplicateCheck.has(imgData.displayUrl)) {
				duplicateCheck.add(imgData.displayUrl)
				sendDataJson(JSON.stringify(imgData),collectLength)
			} else {
				hasEmpty = true;
			}
		}
	}

	// 检测是否完成
	var hasButton = rightBtnClick(collectRootNode, 50);
	    for (var x of duplicateCheck) {
            console.log("collectImg duplicateCheck " + x);
        }
	if(!hasButton) return;

	if (duplicateCheck.size != collectLength) { // 没有完成，则超时继续检测
		console.log("-------------");
		timeoutCheck(50);
	} else { // 完成
		console.log("collectImg 收集完成");
		checkTimes = 0;
		stepIndex = 3;
		checkStep();
	}
}

/**
 * 滑动到左侧时间最大时间检测
 */
function timeoutNext(timeout) {
		timeoutId = setTimeout(function () {

		}, timeout);
}



/**
 * 第三步，复原
 */
function restoreIndex() {
	if (collectLength != 0) {
		isStartCollect = false;
		var index = getNowIndex(indicatorArrays);
		console.log("restoreIndex index: " + index + " nowIndex: " + nowIndex);
		if (index < nowIndex) { // 右点击
			var delayTime = 300;
			if (rightClassName == null) {
				delayTime = 700;
			}
			rightBtnClick(collectRootNode, delayTime);
			timeoutCheck(100);
			return;
		} else if (index > nowIndex) { // 左点击
			var delayTime = 300;
			if (leftClassName == null) {
				delayTime = 700;
			}
			leftBtnClick(collectRootNode, delayTime)
			timeoutCheck(100);
			return;
		}
		stepIndex = 4;
		checkStep();
	}
}

/**
 * 第四步，上报结束
 */
function reportEndReceive() {
	endReceiveData();
	stepIndex = 5;
	checkStep();
}


/**
 * 滑动到左侧时间最大时间检测
 */
function timeoutCheck(timeout) {

	if (checkTimes < STATUS_MAX_TIMES) {
		checkTimes++;
		console.log("checkTimes !! " + checkTimes);
		timeoutId = setTimeout(function () {
			checkStep();
		}, timeout);
	} else { // 强制进入下一步
		console.log("强制进入下一步");
		checkTimes = 0;
		stepIndex++;
		checkStep();
	}
}

function cancelTimeout() {
	if (timeoutId != -1) {
		clearTimeout(timeoutId);
		timeoutId = -1;
	}
}

function isEmpty(obj) {
	if (typeof obj == "undefined" || obj == null || obj == "") {
		return true;
	} else {
		return false;
	}
}

/**
 * 获取图片Url
 */
function getImagUrl(imgParent) {
	if (imgParent != null) {
		var imageDiv = imgParent.getElementsByClassName("_aagv")[0];
		if (imageDiv != null) {
			var imgSelf = imageDiv.getElementsByTagName("img");
			var imgUrl = imgSelf[0].src;
			if (!isEmpty(imgUrl)) {
				return {
					displayUrl: imgUrl,
					userProfile:userProfile,
					videoUrl: ""
				}
			}
		}
	}
	return null;
}

/**
 * 获取视频Url
 */
function getVideoUrl(videoParent) {
	if (videoParent != null) {
		var videoTag = videoParent.getElementsByTagName("video")[0];
		if (videoTag != null) {
			var videoUrl = videoTag.src;
			console.log("videoUrl imgUrl: " + videoUrl);
			if (!videoUrl) {
				var sources = videoTag.getElementsByTagName("source")
				if (sources.length > 0) {
					videoUrl = sources[0].src
				}
			}
			var thumbnailUrl = videoTag.poster
			if (thumbnailUrl && thumbnailUrl.indexOf("base64") >= 0) {
				//story视频
				var imgs = videoParent.getElementsByTagName("IMG")
				var thumbnailUrl = ""
				if (imgs.length > 0) {
					thumbnailUrl = imgs[0].src
				}
			}
			if (videoUrl && !videoUrl.startsWith("blob") && /[^\s]/.test(videoUrl)) {
				return {
					displayUrl: thumbnailUrl,
					userProfile:userProfile,
					videoUrl: videoUrl
				}
			} else {
				console.log("非法ins地址::: " + videoUrl)
			}
		}
	}
	return null;
}

/**
 * 通过指示器获取当前下标
 */
function getNowIndex(indicatorArrays) {
	var confirmLength = -1
	var curIndex = -1
	if (indicatorArrays != null) {
		for (var i = 0; i < indicatorArrays.children.length; i++) {
			var curIndicator = indicatorArrays.children[i]
			var nowClassListLength = curIndicator.classList.length
			if (curIndex == -1) {
				confirmLength = nowClassListLength;
				curIndex = i
			} else if (nowClassListLength > confirmLength) {
				return i;
			} else if (nowClassListLength < confirmLength) {
				return curIndex;
			}
		}
	}
	return -1;
}

/**
 * 点击左侧按钮
 */
function leftBtnClick(parentNode, checkTime) {
	if (checkTime > 0) {
		var timestamp = new Date().getTime();
		console.log("leftBtnClick timestamp: " + timestamp + " lastStamp: " + lastStamp + " diff: " + (
			timestamp - lastStamp) + " checkTime: " + checkTime);
		if (timestamp - lastStamp < checkTime) {
			return;
		}
	}
	var leftBtn = getLeftBtnEle(parentNode);
	console.log("leftBtnClick enter ==> ");
	if (leftBtn != null) {
		leftBtn.click();
		console.log("leftBtnClick click");
		return true // 按钮存在
	} else {
		console.log("leftBtn is null")
	}
	return false; // 按钮不存在
}

/**
 * 点击右侧按钮
 */
function rightBtnClick(parentNode, checkTime) {
		var timestamp = new Date().getTime();
		console.log("rightBtnClick timestamp: " + timestamp + " lastStamp: " + lastStamp + " diff: " + (
			timestamp - lastStamp) + " checkTime: " + checkTime);
		if (timestamp - lastStamp < checkTime) {
			return false;
		}
	lastStamp = timestamp;

	var rightBtn = getRightBtnEle(parentNode);
	console.log("rightBtnClick enter ==> ");
	if (rightBtn != null) {
		rightBtn.click();
		console.log("rightBtnClick click");
		return true; // 按钮存在
	} else {
		console.log("rightBtnClick is null")
		return false;
	}
}

var observerOptions = {
	childList: true, //story需要添加
	attributes: true, // 观察属性变动
	subtree: true, // 观察后代节点，默认为 false
	attributeFilter: ['style', 'class']
}

var observer = new MutationObserver(function (mutations, observer) {
	mutations.forEach((mutation) => {
		switch (mutation.type) {
			case 'childList':
				mutation.addedNodes.forEach((node) => {
					if (isStartCollect) {
						checkTimes = 0;
						//checkStep();
					}
					console.log("mutation.addedNodes: " + node.className + " tagName: " + node.tagName)
					if ((node.tagName == "DIV" || node.tagName == "SECTION" || node.tagName == "LI" || node.tagName == "ARTICLE" || node.tagName == "IMG") && node
						.className != "ins_dl" ) {
						findAllMedias(document, "childList")
					}
				});
				break;
//			case 'attributes':
//				var node = mutation.target;
//				if (isStartCollect) {
//					checkTimes = 0;
//					checkStep();
//				}
//				console.log("mutation.attributes: " + node.className + " tagName: " + node.tagName)
//				if ((node.tagName == "DIV" || node.tagName == "SECTION") && node
//					.className != "ins_dl") {
//					findAllMedias(document, "attributes")
//				}
//				break;
		}
	});
});

// ======== 获取元素标签 ==========
function getIndicatorArray(rootNode) {
	var contentRoot = rootNode.children[1]
	if (contentRoot != null) {
		var contentParent = contentRoot.children[0];
		if (contentParent != null) {
			return contentParent.children[1]
		}
	}
	console.log("getIndicatorArray null")
	return null
}

function getLeftBtnEle(rootNode) {
	if (isEmpty(leftClassName)) {
		var index = getNowIndex(indicatorArrays);
		if (index <= 0) {
			return null
		}
		var ulE = rootNode.getElementsByTagName("ul")[0];
		if (ulE != null) {
			var btnParent = ulE.parentNode.parentNode.parentNode;
			if (btnParent != null) {
				var btnParentLength = btnParent.children.length;
				if (btnParentLength > 1) {
					var btnEs = btnParent.children[1];
					console.log("getLeftBtnEle btnEs: " + btnEs + " btnEs.tagName: " + btnEs.tagName + " btnParentLength: " + btnParentLength + " index: " + index)
					if (btnEs != null && btnEs.tagName == "BUTTON") {
						leftClassName = btnEs.className;
					}
				}
			}
		}
		console.log("leftClassName: " + leftClassName)
	}
	if (isEmpty(leftClassName)) {
		return null
	}
	return rootNode.getElementsByClassName(leftClassName)[0]
}

function getRightBtnEle(rootNode) {
	console.log("getRightBtnEle: " + rightClassName)
	if (isEmpty(rightClassName)) {
		var index = getNowIndex(indicatorArrays);
		if (index == collectLength - 1) {
			return null
		}
		var ulE = rootNode.getElementsByTagName("ul")[0];
		if (ulE != null) {
			var btnParent = ulE.parentNode.parentNode.parentNode;
			if (btnParent != null) {
				var btnParentLength = btnParent.children.length;
				if (btnParentLength > 1) {
					var btnEs = btnParent.children[btnParentLength - 1];
					console.log("getRightBtnEle btnEs: " + btnEs + " btnEs.tagName: " + btnEs.tagName)
					if (btnEs != null && btnEs.tagName == "BUTTON") {
						rightClassName = btnEs.className;
					}
				}
			}
		}
		console.log("rightClassName: " + rightClassName)
	}
	if (isEmpty(rightClassName)) {
		return null
	}
	return rootNode.getElementsByClassName(rightClassName)[0]
}

console.log(" ======== 开始监听元素变化 ===== ");
findAllMedias(document, "all")
observer.observe(document.body, observerOptions);
console.log("完成加载 <<<<<<<<<<<<<<<<<<")

