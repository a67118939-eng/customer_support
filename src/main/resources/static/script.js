const WHATSAPP_URL = "https://wa.me/966554370544?text=Hello%2C%20I%20need%20help%20from%20customer%20support.";
const REDUCED_DECORATIVE_MOTION = true;

const api = {
    faqs: "/api/faqs",
    chatAsk: "/api/chat/ask",
    chatHistory: "/api/chat/history",
    aiAsk: "/api/ai/ask",
    aiModes: "/api/ai/modes",
    aiTokenUsage: "/api/ai/token-usage",
    stats: "/api/dashboard/stats",
    dashboardHistory: "/api/dashboard/history",
    unanswered: "/api/dashboard/unanswered",
    categories: "/api/categories",
    tickets: "/api/tickets",
    feedback: "/api/feedback",
    securityStats: "/api/security/stats",
    auditLogs: "/api/security/audit-logs",
    honeypotEvents: "/api/security/honeypot-events",
    blockedIps: "/api/security/blocked-ips"
};

const ADMIN_ONLY_SECTIONS = new Set([
    "dashboard",
    "ai-learning",
    "unanswered",
    "feedback",
    "security-center"
]);

const ADMIN_ONLY_CONTAINERS = new Set([
    "dashboardStats",
    "pendingGeneratedFaqList",
    "generatedFaqList",
    "unansweredList",
    "feedbackSummary",
    "feedbackList",
    "securityStats",
    "honeypotEventsList",
    "blockedIpsList",
    "auditLogsList"
]);

const translations = {
    EN: {
        swagger: "Swagger",
        english: "English",
        arabic: "Arabic",
        search: "Search",
        refresh: "Refresh",
        save: "Save",
        cancel: "Cancel",
        delete: "Delete",
        edit: "Edit",
        add: "Add",
        update: "Update",
        submit: "Submit",
        clear: "Clear",
        loading: "Loading",
        all: "All",
        allStatuses: "All statuses",
        allLanguages: "All languages",
        noData: "No data available yet.",
        supportPlatform: "Support Platform",
        customerChat: "Customer Chat",
        faqDatabaseAgent: "FAQ Database Agent",
        realAiAgent: "Real AI Agent",
        faqManagement: "FAQ Management",
        aiLearningCenter: "AI Learning Center",
        aiLearningSubtitle: "Review AI-generated FAQs before they become active FAQ answers.",
        pendingReviewFaqs: "Pending review FAQs",
        aiGeneratedFaqs: "AI-generated FAQs",
        approve: "Approve",
        reject: "Reject",
        aiLearningSaved: "This AI answer was saved as a new FAQ for future use.",
        categories: "Categories",
        dashboard: "Dashboard",
        chatHistory: "Chat History",
        unansweredQuestions: "Unanswered Questions",
        supportTickets: "Support Tickets",
        feedback: "Feedback",
        securityCenter: "Security Center",
        securitySubtitle: "Monitor audit logs, honeypot decoy triggers, and temporary IP blocks.",
        honeypotDecoyTrap: "Honeypot Decoy Trap",
        honeypotExplanation: "Honeypot decoy files are fake important-looking resources. If a bot or attacker tries to access them, the system logs the attempt and can temporarily block the IP.",
        honeypotEvents: "Honeypot Events",
        blockedIps: "Blocked IPs",
        auditLogs: "Audit Logs",
        criticalEvents: "Critical Events",
        highEvents: "High Events",
        rateLimitEvents: "Rate Limit Events",
        promptInjectionAttempts: "Prompt Injection Attempts",
        ipAddress: "IP Address",
        userAgent: "User-Agent",
        requestPath: "Path",
        eventType: "Event Type",
        severity: "Severity",
        details: "Details",
        expiresDate: "Expires date",
        unblock: "Unblock",
        customerChatSubtitle: "Chat with Real AI, or switch to FAQ mode for database-only support answers.",
        tokenFeatureLine: "AI token usage counts only when the Real AI API answers. Users get 20,000 tokens; admins are unlimited.",
        faqAgentPageSubtitle: "Use approved FAQ records for safe database-backed answers.",
        realAiPageSubtitle: "Use the backend AI service while keeping secrets on the server.",
        faqManagementSubtitle: "Create, edit, search, and publish FAQ answers used by the database agent.",
        categoriesSubtitle: "Organize FAQs by support topic and language.",
        dashboardSubtitle: "Monitor FAQ coverage, support demand, ticket status, and feedback quality.",
        ticketsSubtitle: "Create and track human support follow-ups.",
        feedbackSubtitle: "Review answer quality signals from customers.",
        askQuestion: "Ask a question",
        send: "Send",
        voiceInput: "Record",
        voiceIdle: "Voice input ready.",
        voiceListening: "Recording... click again to send.",
        voiceProcessing: "Processing voice message...",
        voiceUnsupported: "Voice input is not supported in this browser.",
        voiceCaptured: "Voice message sent. The agent is listening.",
        voiceNoSpeech: "Could not process that voice message. Try again.",
        voicePermissionDenied: "Microphone access was blocked. Allow microphone permission and try again.",
        selectAgentMode: "Select Agent Mode",
        selectLanguage: "Select Language",
        question: "Question",
        answer: "Answer",
        category: "Category",
        status: "Status",
        language: "Language",
        priority: "Priority",
        keywords: "Keywords",
        active: "Active",
        inactive: "Inactive",
        draft: "Draft",
        archived: "Archived",
        confidenceScore: "Confidence Score",
        suggestedQuestions: "Suggested Questions",
        contactHumanSupport: "Contact Human Support",
        createTicket: "Create Ticket",
        answerHelpful: "Was this answer helpful?",
        yes: "Yes",
        no: "No",
        addFaq: "Add FAQ",
        editFaq: "Edit FAQ",
        deleteFaq: "Delete FAQ",
        totalFaqs: "Total FAQs",
        activeFaqs: "Active FAQs",
        totalQuestions: "Total Questions",
        answeredQuestions: "Answered Questions",
        unansweredQuestionsStat: "Unanswered Questions",
        averageConfidence: "Average Confidence",
        openTickets: "Open Tickets",
        resolvedTickets: "Resolved Tickets",
        helpfulFeedback: "Helpful Feedback",
        unhelpfulFeedback: "Unhelpful Feedback",
        chatHistorySubtitle: "View all customer questions, AI responses, confidence scores, and support escalation results.",
        noChatHistory: "No chat history yet.",
        firstConversationHint: "Ask a question in the Customer Chat section to create the first conversation.",
        chatHistoryError: "Unable to load chat history. Please make sure the backend is running.",
        clearHistory: "Clear History",
        unansweredSubtitle: "Review questions that the AI could not answer and convert them into FAQs or escalate them to support.",
        noUnanswered: "No unanswered questions yet.",
        unansweredHint: "When the AI cannot answer a customer question, it will appear here.",
        unansweredError: "Unable to load unanswered questions. Please make sure the backend is running.",
        markReviewed: "Mark Reviewed",
        resolve: "Resolve",
        convertToFaq: "Convert to FAQ",
        escalateToTicket: "Escalate to Ticket",
        newStatus: "New",
        reviewed: "Reviewed",
        resolved: "Resolved",
        escalated: "Escalated",
        convertedToFaq: "Converted to FAQ",
        customerName: "Customer Name",
        customerEmail: "Customer Email",
        subject: "Subject",
        message: "Message",
        adminReply: "Admin Reply",
        description: "Description",
        nameEnglish: "Name English",
        nameArabic: "Name Arabic",
        chatPlaceholder: "How can we help?",
        keywordsPlaceholder: "password, login, account",
        chatHint: "Real AI answers general questions. FAQ Database Agent answers only from saved FAQs.",
        quickActions: "Quick Actions",
        faqAgentDescription: "Answers only from approved FAQ records. If no saved answer exists, it redirects to support.",
        realAiDescription: "Answers general questions like a chat assistant. API keys stay on the server.",
        noFaqs: "No FAQs yet. Add the first FAQ to start training the FAQ Database Agent.",
        faqError: "Unable to load FAQs. Please make sure the backend is running.",
        dashboardStatsUnavailable: "Dashboard stats API is not available yet.",
        answered: "Answered",
        unanswered: "Unanswered",
        mode: "Mode",
        inputType: "Input type",
        createdDate: "Created date",
        resolvedDate: "Resolved date",
        categoryGuess: "Category guess",
        adminNote: "Admin note",
        apiWaiting: "This feature is waiting for backend data.",
        saved: "Saved successfully.",
        deleted: "Deleted successfully.",
        clearHistoryUnavailable: "Clear history API is not available yet.",
        feedbackSaved: "Feedback saved.",
        unableToComplete: "Unable to complete the request.",
        adminOnlyPage: "this is admin page only",
        noTickets: "No support tickets yet.",
        noFeedback: "No feedback yet.",
        noCategories: "No categories yet.",
        supportOpened: "Opening human support.",
        typing: "AI is typing"
    },
    AR: {
        swagger: "Swagger",
        english: "الإنجليزية",
        arabic: "العربية",
        search: "بحث",
        refresh: "تحديث",
        save: "حفظ",
        cancel: "إلغاء",
        delete: "حذف",
        edit: "تعديل",
        add: "إضافة",
        update: "تحديث",
        submit: "إرسال",
        clear: "مسح",
        loading: "جار التحميل",
        all: "الكل",
        allStatuses: "كل الحالات",
        allLanguages: "كل اللغات",
        noData: "لا توجد بيانات حتى الآن.",
        supportPlatform: "منصة الدعم",
        customerChat: "محادثة العميل",
        faqDatabaseAgent: "وكيل قاعدة الأسئلة الشائعة",
        realAiAgent: "وكيل الذكاء الاصطناعي الحقيقي",
        faqManagement: "إدارة الأسئلة الشائعة",
        categories: "التصنيفات",
        dashboard: "لوحة التحكم",
        chatHistory: "سجل المحادثات",
        unansweredQuestions: "الأسئلة غير المجابة",
        supportTickets: "تذاكر الدعم",
        feedback: "التقييمات",
        customerChatSubtitle: "اطرح الأسئلة واحصل على إجابات ذكية مع إمكانية التحويل إلى الدعم البشري.",
        faqAgentPageSubtitle: "استخدم سجلات الأسئلة المعتمدة لإجابات آمنة من قاعدة البيانات.",
        realAiPageSubtitle: "استخدم خدمة الذكاء الاصطناعي في الخلفية مع بقاء الأسرار على الخادم.",
        faqManagementSubtitle: "أنشئ وعدل وابحث وانشر إجابات الأسئلة التي يستخدمها وكيل قاعدة البيانات.",
        categoriesSubtitle: "نظم الأسئلة حسب موضوع الدعم واللغة.",
        dashboardSubtitle: "راقب تغطية الأسئلة والطلب على الدعم وحالة التذاكر وجودة التقييم.",
        ticketsSubtitle: "أنشئ وتابع طلبات الدعم البشري.",
        feedbackSubtitle: "راجع مؤشرات جودة الإجابات من العملاء.",
        askQuestion: "اطرح سؤالاً",
        send: "إرسال",
        selectAgentMode: "اختر وضع الوكيل",
        selectLanguage: "اختر اللغة",
        question: "السؤال",
        answer: "الإجابة",
        category: "التصنيف",
        status: "الحالة",
        language: "اللغة",
        priority: "الأولوية",
        keywords: "الكلمات المفتاحية",
        active: "نشط",
        inactive: "غير نشط",
        draft: "مسودة",
        archived: "مؤرشف",
        confidenceScore: "درجة الثقة",
        suggestedQuestions: "أسئلة مقترحة",
        contactHumanSupport: "التواصل مع الدعم البشري",
        createTicket: "إنشاء تذكرة",
        answerHelpful: "هل كانت هذه الإجابة مفيدة؟",
        yes: "نعم",
        no: "لا",
        addFaq: "إضافة سؤال شائع",
        editFaq: "تعديل سؤال شائع",
        deleteFaq: "حذف سؤال شائع",
        totalFaqs: "إجمالي الأسئلة",
        activeFaqs: "الأسئلة النشطة",
        totalQuestions: "إجمالي الأسئلة",
        answeredQuestions: "الأسئلة المجابة",
        unansweredQuestionsStat: "الأسئلة غير المجابة",
        averageConfidence: "متوسط الثقة",
        openTickets: "التذاكر المفتوحة",
        resolvedTickets: "التذاكر المحلولة",
        helpfulFeedback: "تقييمات مفيدة",
        unhelpfulFeedback: "تقييمات غير مفيدة",
        chatHistorySubtitle: "اعرض كل أسئلة العملاء وردود الذكاء الاصطناعي ودرجات الثقة ونتائج التحويل للدعم.",
        noChatHistory: "لا يوجد سجل محادثات حتى الآن.",
        firstConversationHint: "اطرح سؤالاً في قسم محادثة العميل لإنشاء أول محادثة.",
        chatHistoryError: "تعذر تحميل سجل المحادثات. يرجى التأكد من تشغيل الخادم.",
        clearHistory: "مسح السجل",
        unansweredSubtitle: "راجع الأسئلة التي لم يتمكن الذكاء الاصطناعي من إجابتها وحولها إلى أسئلة شائعة أو تذاكر دعم.",
        noUnanswered: "لا توجد أسئلة غير مجابة حتى الآن.",
        unansweredHint: "عندما لا يستطيع الذكاء الاصطناعي إجابة سؤال العميل، سيظهر هنا.",
        unansweredError: "تعذر تحميل الأسئلة غير المجابة. يرجى التأكد من تشغيل الخادم.",
        markReviewed: "تحديد كمراجع",
        resolve: "حل",
        convertToFaq: "تحويل إلى سؤال شائع",
        escalateToTicket: "تصعيد إلى تذكرة",
        newStatus: "جديد",
        reviewed: "تمت المراجعة",
        resolved: "تم الحل",
        escalated: "تم التصعيد",
        convertedToFaq: "تم التحويل إلى سؤال شائع",
        customerName: "اسم العميل",
        customerEmail: "بريد العميل",
        subject: "الموضوع",
        message: "الرسالة",
        adminReply: "رد المسؤول",
        description: "الوصف",
        nameEnglish: "الاسم بالإنجليزية",
        nameArabic: "الاسم بالعربية",
        chatPlaceholder: "كيف يمكننا مساعدتك؟",
        keywordsPlaceholder: "كلمة المرور، تسجيل الدخول، الحساب",
        chatHint: "اختر وضع الوكيل واللغة قبل إرسال السؤال.",
        quickActions: "إجراءات سريعة",
        faqAgentDescription: "يجيب فقط من سجلات الأسئلة الشائعة المعتمدة ويصعد الأسئلة غير المجابة.",
        realAiDescription: "يستخدم خدمة الذكاء الاصطناعي في الخلفية مع سياق الأسئلة الشائعة. تبقى مفاتيح API على الخادم.",
        noFaqs: "لا توجد أسئلة شائعة حتى الآن. أضف أول سؤال لبدء تدريب وكيل قاعدة الأسئلة.",
        faqError: "تعذر تحميل الأسئلة الشائعة. يرجى التأكد من تشغيل الخادم.",
        dashboardStatsUnavailable: "واجهة إحصاءات لوحة التحكم غير متاحة بعد.",
        answered: "مجاب",
        unanswered: "غير مجاب",
        mode: "الوضع",
        inputType: "نوع الإدخال",
        createdDate: "تاريخ الإنشاء",
        resolvedDate: "تاريخ الحل",
        categoryGuess: "التصنيف المتوقع",
        adminNote: "ملاحظة المسؤول",
        apiWaiting: "هذه الميزة بانتظار بيانات الخلفية.",
        saved: "تم الحفظ بنجاح.",
        deleted: "تم الحذف بنجاح.",
        clearHistoryUnavailable: "واجهة مسح السجل غير متاحة بعد.",
        feedbackSaved: "تم حفظ التقييم.",
        unableToComplete: "تعذر إكمال الطلب.",
        adminOnlyPage: "this is admin page only",
        noTickets: "لا توجد تذاكر دعم حتى الآن.",
        noFeedback: "لا توجد تقييمات حتى الآن.",
        noCategories: "لا توجد تصنيفات حتى الآن.",
        supportOpened: "جار فتح الدعم البشري.",
        typing: "الذكاء الاصطناعي يكتب"
    }
};

const state = {
    language: "EN",
    currentSection: "customer-chat",
    faqs: [],
    generatedFaqs: [],
    pendingGeneratedFaqs: [],
    categories: [],
    chatHistory: [],
    unanswered: [],
    tickets: [],
    feedback: [],
    securityStats: {},
    auditLogs: [],
    honeypotEvents: [],
    blockedIps: [],
    lastChatHistoryId: null,
    tokenUsage: {
        tokensUsed: 0,
        tokenLimit: 20000,
        tokensRemaining: 20000,
        percentUsed: 0,
        lastRequestTokens: 0,
        openAiUsed: false,
        tokenUsageReason: "No AI request yet."
    },
    curvedLoop: {
        frameId: null,
        lastTime: 0,
        offset: 0
    },
    menu: {
        open: false
    },
    staggeredMenu: {
        open: false,
        textTimeout: null
    },
    clickSpark: {
        canvas: null,
        context: null,
        sparks: [],
        animationId: null,
        dpr: 1,
        config: {
            sparkColor: "#ffffff",
            sparkSize: 10,
            sparkRadius: 18,
            sparkCount: 8,
            duration: 420,
            easing: "ease-out",
            extraScale: 1.25
        }
    },
    auth: {
        user: null,
        csrfToken: localStorage.getItem("supportFaqCsrf") || ""
    },
    voice: {
        recognition: null,
        recorder: null,
        stream: null,
        chunks: [],
        transcript: "",
        listening: false,
        supported: false,
        speechSupported: false,
        inputFromVoice: false
    }
};

document.addEventListener("DOMContentLoaded", initApp);

class ApiRequestError extends Error {
    constructor(message, status, data) {
        super(message);
        this.name = "ApiRequestError";
        this.status = status;
        this.data = data;
    }
}

async function initApp() {
    if (!REDUCED_DECORATIVE_MOTION) {
        initClickSpark();
    }
    await loadCurrentUser();
    initRushPageScene();
    bindEvents();
    initVoiceInput();
    setMenuOpen(false);
    initBorderGlow();
    loadLanguage();
    seedChatWelcome();
    loadTokenUsage();
    showSection("customer-chat");
    const startupLoads = [
        loadFaqs(),
        loadCategories()
    ];
    if (isAdmin()) {
        startupLoads.push(
            loadDashboardStats(),
            loadAiLearningCenter(),
            loadChatHistory(),
            loadUnansweredQuestions(),
            loadTickets(),
            loadFeedback(),
            loadSecurityCenter()
        );
    }
    Promise.allSettled(startupLoads);
}

function initClickSpark() {
    const canvas = byId("clickSparkCanvas");
    if (!canvas || state.clickSpark.canvas) return;

    const context = canvas.getContext("2d");
    if (!context) return;

    state.clickSpark.canvas = canvas;
    state.clickSpark.context = context;

    const resizeCanvas = () => {
        const dpr = Math.max(1, window.devicePixelRatio || 1);
        state.clickSpark.dpr = dpr;
        const width = Math.ceil(window.innerWidth);
        const height = Math.ceil(window.innerHeight);
        const targetWidth = Math.ceil(width * dpr);
        const targetHeight = Math.ceil(height * dpr);
        if (canvas.width !== targetWidth || canvas.height !== targetHeight) {
            canvas.width = targetWidth;
            canvas.height = targetHeight;
            canvas.style.width = `${width}px`;
            canvas.style.height = `${height}px`;
            context.setTransform(dpr, 0, 0, dpr, 0, 0);
        }
    };

    resizeCanvas();
    window.addEventListener("resize", resizeCanvas);
    document.addEventListener("click", handleClickSpark);
}

function initBorderGlow() {
    document.querySelectorAll(".border-glow-card").forEach(card => {
        if (card.dataset.borderGlowReady === "true") return;
        card.dataset.borderGlowReady = "true";
        setBorderGlowVars(card);
        card.addEventListener("pointermove", handleBorderGlowPointerMove);
        card.addEventListener("pointerleave", () => {
            card.style.setProperty("--edge-proximity", "0");
        });
    });
}

function setBorderGlowVars(card) {
    const glowVars = buildBorderGlowVars(card.dataset.glowColor || "230 92 82", 1);
    Object.entries(glowVars).forEach(([key, value]) => card.style.setProperty(key, value));
    card.style.setProperty("--gradient-one", "radial-gradient(at 80% 55%, rgba(166, 200, 255, 0.42) 0px, transparent 50%)");
    card.style.setProperty("--gradient-two", "radial-gradient(at 18% 10%, rgba(82, 39, 255, 0.34) 0px, transparent 50%)");
    card.style.setProperty("--gradient-three", "radial-gradient(at 86% 85%, rgba(255, 159, 252, 0.32) 0px, transparent 50%)");
}

function buildBorderGlowVars(glowColor, intensity) {
    const parsed = parseHslParts(glowColor);
    const base = `${parsed.h}deg ${parsed.s}% ${parsed.l}%`;
    const opacities = [100, 60, 50, 40, 30, 20, 10];
    const keys = ["", "-60", "-50", "-40", "-30", "-20", "-10"];
    return keys.reduce((vars, key, index) => {
        vars[`--glow-color${key}`] = `hsl(${base} / ${Math.min(opacities[index] * intensity, 100)}%)`;
        return vars;
    }, {});
}

function parseHslParts(hslString) {
    const match = String(hslString).match(/([\d.]+)\s+([\d.]+)%?\s+([\d.]+)%?/);
    if (!match) {
        return { h: 230, s: 92, l: 82 };
    }
    return {
        h: Number(match[1]),
        s: Number(match[2]),
        l: Number(match[3])
    };
}

function handleBorderGlowPointerMove(event) {
    const card = event.currentTarget;
    const rect = card.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;
    const centerX = rect.width / 2;
    const centerY = rect.height / 2;
    const dx = x - centerX;
    const dy = y - centerY;
    const kx = dx === 0 ? Infinity : centerX / Math.abs(dx);
    const ky = dy === 0 ? Infinity : centerY / Math.abs(dy);
    const edge = Math.min(Math.max(1 / Math.min(kx, ky), 0), 1);
    const angle = ((Math.atan2(dy, dx) * 180) / Math.PI + 450) % 360;

    card.style.setProperty("--edge-proximity", `${(edge * 100).toFixed(3)}`);
    card.style.setProperty("--cursor-angle", `${angle.toFixed(3)}deg`);
}

function triggerBorderGlowSweep() {
    document.querySelectorAll("#staggeredMenuPanel .border-glow-card").forEach((card, index) => {
        const angleStart = 110;
        const angleEnd = 465;
        card.classList.add("sweep-active");
        card.style.setProperty("--cursor-angle", `${angleStart}deg`);
        animateBorderGlowValue({
            delay: index * 35,
            duration: 520,
            onUpdate: value => card.style.setProperty("--edge-proximity", value.toFixed(3))
        });
        animateBorderGlowValue({
            delay: index * 35,
            duration: 1250,
            end: 100,
            onUpdate: value => {
                const angle = angleStart + (angleEnd - angleStart) * (value / 100);
                card.style.setProperty("--cursor-angle", `${angle.toFixed(3)}deg`);
            }
        });
        animateBorderGlowValue({
            delay: 900 + index * 35,
            duration: 700,
            start: 100,
            end: 0,
            ease: easeInCubic,
            onUpdate: value => card.style.setProperty("--edge-proximity", value.toFixed(3)),
            onEnd: () => card.classList.remove("sweep-active")
        });
    });
}

function animateBorderGlowValue({ start = 0, end = 100, duration = 1000, delay = 0, ease = easeOutCubic, onUpdate, onEnd }) {
    const startTime = performance.now() + delay;
    const tick = () => {
        const elapsed = performance.now() - startTime;
        const progress = Math.min(Math.max(elapsed / duration, 0), 1);
        onUpdate(start + (end - start) * ease(progress));
        if (progress < 1) {
            requestAnimationFrame(tick);
        } else if (onEnd) {
            onEnd();
        }
    };
    setTimeout(() => requestAnimationFrame(tick), delay);
}

function easeOutCubic(progress) {
    return 1 - Math.pow(1 - progress, 3);
}

function easeInCubic(progress) {
    return progress * progress * progress;
}

function handleClickSpark(event) {
    const { canvas, config } = state.clickSpark;
    if (!canvas || window.matchMedia("(prefers-reduced-motion: reduce)").matches) return;

    const rect = canvas.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;
    const now = performance.now();
    const newSparks = Array.from({ length: config.sparkCount }, (_, index) => ({
        x,
        y,
        angle: (2 * Math.PI * index) / config.sparkCount,
        startTime: now
    }));

    state.clickSpark.sparks.push(...newSparks);
    if (!state.clickSpark.animationId) {
        state.clickSpark.animationId = requestAnimationFrame(drawClickSparks);
    }
}

function drawClickSparks(timestamp) {
    const { canvas, context, config } = state.clickSpark;
    if (!canvas || !context) return;

    context.clearRect(0, 0, canvas.width, canvas.height);
    state.clickSpark.sparks = state.clickSpark.sparks.filter(spark => {
        const elapsed = timestamp - spark.startTime;
        if (elapsed >= config.duration) {
            return false;
        }

        const progress = elapsed / config.duration;
        const eased = easeClickSpark(progress, config.easing);
        const distance = eased * config.sparkRadius * config.extraScale;
        const lineLength = config.sparkSize * (1 - eased);
        const x1 = spark.x + distance * Math.cos(spark.angle);
        const y1 = spark.y + distance * Math.sin(spark.angle);
        const x2 = spark.x + (distance + lineLength) * Math.cos(spark.angle);
        const y2 = spark.y + (distance + lineLength) * Math.sin(spark.angle);

        context.strokeStyle = config.sparkColor;
        context.lineWidth = 2;
        context.lineCap = "round";
        context.globalAlpha = 1 - eased * 0.25;
        context.beginPath();
        context.moveTo(x1, y1);
        context.lineTo(x2, y2);
        context.stroke();
        context.globalAlpha = 1;

        return true;
    });

    if (state.clickSpark.sparks.length > 0) {
        state.clickSpark.animationId = requestAnimationFrame(drawClickSparks);
    } else {
        context.clearRect(0, 0, canvas.width, canvas.height);
        state.clickSpark.animationId = null;
    }
}

function easeClickSpark(progress, easing) {
    if (easing === "linear") return progress;
    if (easing === "ease-in") return progress * progress;
    if (easing === "ease-in-out") {
        return progress < 0.5
            ? 2 * progress * progress
            : -1 + (4 - 2 * progress) * progress;
    }
    return progress * (2 - progress);
}

async function loadCurrentUser() {
    try {
        const response = await fetch("/api/auth/me", { credentials: "same-origin" });
        if (!response.ok) {
            redirectToLogin();
            return;
        }
        const user = await response.json();
        state.auth.user = user;
        state.auth.csrfToken = user.csrfToken || "";
        localStorage.setItem("supportFaqCsrf", state.auth.csrfToken);
        localStorage.setItem("supportFaqUser", JSON.stringify({
            fullName: user.fullName,
            email: user.email,
            role: user.role
        }));
        renderCurrentUser();
        applyRoleAccess();
    } catch {
        redirectToLogin();
    }
}

function renderCurrentUser() {
    const user = state.auth.user;
    if (!user) return;
    byId("userName").textContent = user.fullName || user.email || "User";
    byId("userRole").textContent = user.role || "USER";
    byId("userInitials").textContent = initials(user.fullName || user.email || "U");
    if (isAdmin()) {
        renderTokenUsage({
            tokensUsed: state.tokenUsage.tokensUsed || 0,
            tokenLimit: 0,
            tokensRemaining: Number.MAX_SAFE_INTEGER,
            tokenUsageReason: state.tokenUsage.tokenUsageReason
        }, { preserveLastStatus: true });
    }
}

function applyRoleAccess() {
    document.querySelectorAll(".admin-only").forEach(element => {
        element.hidden = !isAdmin();
    });
    if (!isAdmin() && state.currentSection === "security-center") {
        showSection("customer-chat");
    }
}

function isAdmin() {
    return state.auth.user?.role === "ADMIN";
}

function initials(value) {
    return value
        .split(/\s+|@/)
        .filter(Boolean)
        .slice(0, 2)
        .map(part => part[0].toUpperCase())
        .join("") || "U";
}

function initVoiceInput() {
    const button = byId("voiceInputBtn");
    if (!button) return;

    const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
    state.voice.speechSupported = Boolean(SpeechRecognition);
    state.voice.supported = Boolean(navigator.mediaDevices?.getUserMedia && window.MediaRecorder);

    if (!state.voice.supported) {
        state.voice.supported = false;
        button.disabled = true;
        setVoiceStatus(t("voiceUnsupported"));
        return;
    }

    if (!state.voice.speechSupported) {
        setVoiceStatus(t("voiceIdle"));
        return;
    }

    state.voice.recognition = new SpeechRecognition();
    state.voice.recognition.continuous = false;
    state.voice.recognition.interimResults = true;
    updateVoiceLanguage();

    state.voice.recognition.onstart = () => {
        setVoiceStatus(t("voiceListening"));
    };

    state.voice.recognition.onresult = event => {
        let finalTranscript = "";
        let interimTranscript = "";
        for (let index = event.resultIndex; index < event.results.length; index++) {
            const transcript = event.results[index][0].transcript;
            if (event.results[index].isFinal) {
                finalTranscript += transcript;
            } else {
                interimTranscript += transcript;
            }
        }
        const transcript = (finalTranscript || interimTranscript).trim();
        if (transcript) {
            byId("chatInput").value = transcript;
            state.voice.transcript = transcript;
            state.voice.inputFromVoice = Boolean(finalTranscript);
            setVoiceStatus(finalTranscript ? t("voiceCaptured") : transcript);
        }
    };

    state.voice.recognition.onerror = event => {
        console.warn("Speech recognition error", event.error);
    };

    state.voice.recognition.onend = () => {
        if (state.voice.listening) setVoiceStatus(t("voiceListening"));
    };
}

async function toggleVoiceInput() {
    if (!state.voice.supported) {
        setVoiceStatus(t("voiceUnsupported"));
        return;
    }

    if (state.voice.listening) {
        stopVoiceRecording();
        return;
    }

    await startVoiceRecording();
}

async function startVoiceRecording() {
    try {
        state.voice.stream = await navigator.mediaDevices.getUserMedia({ audio: true });
        const options = preferredAudioOptions();
        state.voice.recorder = options
            ? new MediaRecorder(state.voice.stream, { mimeType: options })
            : new MediaRecorder(state.voice.stream);
        state.voice.chunks = [];
        state.voice.transcript = "";

        state.voice.recorder.addEventListener("dataavailable", event => {
            if (event.data && event.data.size > 0) {
                state.voice.chunks.push(event.data);
            }
        });
        state.voice.recorder.addEventListener("stop", finishVoiceRecording, { once: true });
        state.voice.recorder.start();

        updateVoiceLanguage();
        if (state.voice.recognition) {
            try {
                state.voice.recognition.start();
            } catch {
                // The backend still transcribes the uploaded audio.
            }
        }

        state.voice.listening = true;
        byId("voiceInputBtn").classList.add("listening");
        byId("voiceInputBtn").textContent = t("voiceListening");
        setVoiceStatus(t("voiceListening"));
    } catch (error) {
        console.error(error);
        setVoiceStatus(error?.name === "NotAllowedError" ? t("voicePermissionDenied") : t("voiceUnsupported"));
        cleanupVoiceStream();
    }
}

function stopVoiceRecording() {
    if (!state.voice.recorder || state.voice.recorder.state === "inactive") return;
    setVoiceStatus(t("voiceProcessing"));
    byId("voiceInputBtn").disabled = true;
    try {
        state.voice.recorder.stop();
    } catch (error) {
        console.error(error);
        resetVoiceButton();
    }
    if (state.voice.recognition) {
        try {
            state.voice.recognition.stop();
        } catch {
            // Recognition may already be stopped.
        }
    }
}

async function finishVoiceRecording() {
    const mimeType = state.voice.recorder?.mimeType || state.voice.chunks[0]?.type || "audio/webm";
    const audioBlob = new Blob(state.voice.chunks, { type: mimeType });
    const transcript = (byId("chatInput").value || state.voice.transcript || "").trim();
    cleanupVoiceStream();

    if (!audioBlob.size) {
        setVoiceStatus(t("voiceNoSpeech"));
        resetVoiceButton();
        return;
    }

    byId("chatInput").value = "";
    const audioUrl = URL.createObjectURL(audioBlob);
    const userVoiceBubble = appendVoiceBubble(audioUrl, transcript);
    const loadingId = appendChatLoading();

    try {
        const mode = byId("agentModeSelect").value;
        const language = byId("chatLanguageSelect").value;
        const response = await sendVoiceQuestion(audioBlob, { mode, language });
        removeElement(loadingId);
        updateVoiceBubbleTranscript(userVoiceBubble, response.transcript || transcript);
        appendBotResponse({
            answer: response.answer,
            answered: response.answered,
            confidenceScore: response.confidenceScore,
            mode,
            language,
            sessionId: response.sessionId,
            audioBase64: response.audioBase64
        }, mode, language);
        setVoiceStatus(t("voiceCaptured"));
        await Promise.allSettled([loadTokenUsage(true), loadChatHistory(), loadUnansweredQuestions(), loadDashboardStats()]);
    } catch (error) {
        console.error(error);
        removeElement(loadingId);
        appendChatBubble("bot", error.message || t("unableToComplete"));
        showToast(error.message || t("unableToComplete"), "error");
        setVoiceStatus(t("voiceNoSpeech"));
    } finally {
        resetVoiceButton();
    }
}

function preferredAudioOptions() {
    const types = [
        "audio/ogg;codecs=opus",
        "audio/webm;codecs=opus",
        "audio/webm"
    ];
    return types.find(type => MediaRecorder.isTypeSupported(type)) || "";
}

async function sendVoiceQuestion(audioBlob, options = {}) {
    const formData = new FormData();
    const extension = audioBlob.type.includes("ogg") ? "ogg" : "webm";
    formData.append("audio", audioBlob, `voice-message.${extension}`);
    formData.append("mode", options.mode || "REAL_AI");
    formData.append("language", options.language || state.language);
    formData.append("sessionId", getSessionId());

    const headers = {};
    if (state.auth.csrfToken) {
        headers["X-CSRF-Token"] = state.auth.csrfToken;
    }

    const response = await fetch("/api/voice/ask", {
        method: "POST",
        credentials: "same-origin",
        headers,
        body: formData
    });
    const contentType = response.headers.get("content-type") || "";
    const data = contentType.includes("application/json") ? await response.json() : await response.text();
    if (response.status === 401) {
        redirectToLogin();
        throw new Error("Please log in first.");
    }
    if (!response.ok) {
        throw new Error(typeof data === "object" && data ? data.message || data.error : data || t("unableToComplete"));
    }
    return data || {};
}

function cleanupVoiceStream() {
    state.voice.stream?.getTracks().forEach(track => track.stop());
    state.voice.stream = null;
    state.voice.recorder = null;
    state.voice.chunks = [];
    state.voice.listening = false;
}

function resetVoiceButton() {
    const button = byId("voiceInputBtn");
    if (!button) return;
    button.disabled = false;
    button.classList.remove("listening");
    button.textContent = t("voiceInput");
}

function updateVoiceLanguage() {
    if (!state.voice.recognition) return;
    const selectedLanguage = byId("chatLanguageSelect")?.value || state.language;
    state.voice.recognition.lang = selectedLanguage === "AR" ? "ar-SA" : "en-US";
}

function setVoiceStatus(message) {
    const status = byId("voiceStatus");
    if (status) {
        status.textContent = message;
    }
}

async function logout() {
    try {
        await apiRequest("/api/auth/logout", { method: "POST" });
    } finally {
        localStorage.removeItem("supportFaqCsrf");
        localStorage.removeItem("supportFaqUser");
        redirectToLogin();
    }
}

function redirectToLogin() {
    window.location.replace("/login.html");
}

function bindEvents() {
    document.querySelectorAll("[data-section]").forEach(button => {
        button.addEventListener("click", () => {
            showSection(button.dataset.section);
            if (button.closest("#sidebarNav")) closeMenu();
        });
    });
    document.querySelectorAll("[data-stagger-section]").forEach(link => {
        link.addEventListener("click", event => {
            event.preventDefault();
            showSection(link.dataset.staggerSection);
            closeStaggeredMenu();
        });
    });
    document.querySelectorAll("[data-section-link]").forEach(button => {
        button.addEventListener("click", () => showSection(button.dataset.sectionLink));
    });
    document.querySelectorAll("[data-action='open-support']").forEach(button => {
        button.addEventListener("click", () => openWhatsAppSupport());
    });
    const menuToggle = byId("menuToggle");
    if (menuToggle) {
        menuToggle.addEventListener("click", event => {
            addRippleButtonWave(menuToggle, event);
            setMenuOpen(!state.menu.open);
        });
    }
    const menuBackdrop = byId("menuBackdrop");
    if (menuBackdrop) {
        menuBackdrop.addEventListener("click", closeMenu);
    }
    document.addEventListener("keydown", event => {
        if (event.key === "Escape") closeMenu();
    });

    const staggeredMenuToggle = byId("staggeredMenuToggle");
    if (staggeredMenuToggle) {
        staggeredMenuToggle.addEventListener("click", toggleStaggeredMenu);
        document.addEventListener("mousedown", handleStaggeredMenuClickAway);
        document.addEventListener("keydown", event => {
            if (event.key === "Escape") closeStaggeredMenu();
        });
    }
    byId("swaggerBtn")?.addEventListener("click", () => window.open("/swagger-ui.html", "_blank"));
    byId("logoutBtn").addEventListener("click", logout);
    byId("languageSelect").addEventListener("change", event => setLanguage(event.target.value));
    byId("chatLanguageSelect").addEventListener("change", updateVoiceLanguage);
    byId("chatForm").addEventListener("submit", askQuestion);
    byId("voiceInputBtn").addEventListener("click", toggleVoiceInput);
    byId("faqForm").addEventListener("submit", saveFaq);
    byId("resetFaqBtn").addEventListener("click", resetFaqForm);
    byId("cancelFaqBtn").addEventListener("click", resetFaqForm);
    byId("faqRefreshBtn").addEventListener("click", loadFaqs);
    byId("aiLearningRefreshBtn").addEventListener("click", loadAiLearningCenter);
    byId("dashboardRefreshBtn").addEventListener("click", loadDashboardStats);
    byId("historyRefreshBtn").addEventListener("click", loadChatHistory);
    byId("historyClearBtn").addEventListener("click", clearChatHistory);
    byId("unansweredRefreshBtn").addEventListener("click", loadUnansweredQuestions);
    byId("categoryForm").addEventListener("submit", saveCategory);
    byId("ticketForm").addEventListener("submit", saveTicket);
    byId("feedbackRefreshBtn").addEventListener("click", loadFeedback);
    byId("securityRefreshBtn").addEventListener("click", loadSecurityCenter);
    byId("loadAiModesBtn").addEventListener("click", loadAiModes);

    ["faqSearchInput", "faqCategoryFilter", "faqStatusFilter", "faqLanguageFilter"].forEach(id => {
        byId(id).addEventListener("input", () => renderFaqs(state.faqs));
        byId(id).addEventListener("change", () => renderFaqs(state.faqs));
    });
    ["historySearchInput", "historyModeFilter", "historyStatusFilter", "historyLanguageFilter"].forEach(id => {
        byId(id).addEventListener("input", () => renderChatHistory(state.chatHistory));
        byId(id).addEventListener("change", () => renderChatHistory(state.chatHistory));
    });
    ["unansweredSearchInput", "unansweredStatusFilter", "unansweredLanguageFilter"].forEach(id => {
        byId(id).addEventListener("input", () => renderUnansweredQuestions(state.unanswered));
        byId(id).addEventListener("change", () => renderUnansweredQuestions(state.unanswered));
    });
}

function addRippleButtonWave(button, event) {
    const rect = button.getBoundingClientRect();
    const size = Math.max(rect.width, rect.height) * 1.8;
    const wave = document.createElement("span");
    wave.className = "ripple-menu-wave";
    wave.style.width = `${size}px`;
    wave.style.height = `${size}px`;
    wave.style.left = `${event.clientX - rect.left - size / 2}px`;
    wave.style.top = `${event.clientY - rect.top - size / 2}px`;
    button.appendChild(wave);
    setTimeout(() => wave.remove(), 620);
}

function setMenuOpen(open) {
    const isOpen = Boolean(open);
    const sidebar = byId("appSidebar");
    const toggle = byId("menuToggle");
    const backdrop = byId("menuBackdrop");
    state.menu.open = isOpen;
    document.body.classList.toggle("menu-open", isOpen);

    if (sidebar) {
        sidebar.setAttribute("aria-hidden", isOpen ? "false" : "true");
        sidebar.toggleAttribute("inert", !isOpen);
    }
    if (toggle) {
        toggle.setAttribute("aria-expanded", isOpen ? "true" : "false");
        toggle.setAttribute("aria-label", isOpen ? "Close menu" : "Open menu");
        const text = toggle.querySelector(".ripple-menu-text");
        if (text) text.textContent = "Menu";
    }
    if (backdrop) {
        backdrop.setAttribute("aria-hidden", isOpen ? "false" : "true");
    }
}

function closeMenu() {
    if (state.menu.open) setMenuOpen(false);
}

function toggleStaggeredMenu() {
    setStaggeredMenuOpen(!state.staggeredMenu.open);
}

function closeStaggeredMenu() {
    setStaggeredMenuOpen(false);
}

function setStaggeredMenuOpen(open) {
    const menu = byId("staggeredMenu");
    const panel = byId("staggeredMenuPanel");
    const toggle = byId("staggeredMenuToggle");
    const text = byId("staggeredMenuToggleText");
    if (!menu || !panel || !toggle || !text) return;

    state.staggeredMenu.open = Boolean(open);
    menu.dataset.open = open ? "true" : "false";
    menu.setAttribute("aria-hidden", open ? "false" : "true");
    panel.setAttribute("aria-hidden", open ? "false" : "true");
    toggle.setAttribute("aria-expanded", open ? "true" : "false");
    toggle.setAttribute("aria-label", open ? "Close menu" : "Open menu");
    document.body.classList.toggle("staggered-menu-open", open);

    clearTimeout(state.staggeredMenu.textTimeout);
    text.innerHTML = "";
    const sequence = open ? ["Menu", "Close"] : ["Close", "Menu"];
    sequence.forEach(label => {
        const line = document.createElement("span");
        line.className = "sm-toggle-line";
        line.textContent = label;
        text.appendChild(line);
    });
    text.style.transform = "translateY(0)";
    requestAnimationFrame(() => {
        text.style.transform = "translateY(-50%)";
    });
    state.staggeredMenu.textTimeout = setTimeout(() => {
        text.innerHTML = `<span class="sm-toggle-line">${open ? "Close" : "Menu"}</span>`;
        text.style.transform = "translateY(0)";
    }, 480);
    if (open) {
        triggerBorderGlowSweep();
    }
}

function handleStaggeredMenuClickAway(event) {
    if (!state.staggeredMenu.open) return;
    const panel = byId("staggeredMenuPanel");
    const toggle = byId("staggeredMenuToggle");
    if ((panel && panel.contains(event.target)) || (toggle && toggle.contains(event.target))) {
        return;
    }
    closeStaggeredMenu();
}

function loadLanguage() {
    const saved = localStorage.getItem("supportFaqLang");
    setLanguage(saved === "AR" ? "AR" : "EN");
}

function setLanguage(lang) {
    state.language = lang === "AR" ? "AR" : "EN";
    localStorage.setItem("supportFaqLang", state.language);
    byId("languageSelect").value = state.language;
    byId("chatLanguageSelect").value = state.language;
    setDirection(state.language);
    applyTranslations();
    updateVoiceLanguage();
    setVoiceStatus(state.voice.supported ? t("voiceIdle") : t("voiceUnsupported"));
    showSection(state.currentSection);
}

function applyTranslations() {
    document.querySelectorAll("[data-i18n]").forEach(element => {
        element.textContent = t(element.dataset.i18n);
    });
    document.querySelectorAll("[data-i18n-placeholder]").forEach(element => {
        element.setAttribute("placeholder", t(element.dataset.i18nPlaceholder));
    });
}

function setDirection(lang) {
    const isArabic = lang === "AR";
    document.documentElement.lang = isArabic ? "ar" : "en";
    document.documentElement.dir = isArabic ? "rtl" : "ltr";
    document.body.dir = isArabic ? "rtl" : "ltr";
    document.body.classList.toggle("rtl", isArabic);
    document.body.classList.toggle("ltr", !isArabic);
}

function setCurvedSubtitle(text) {
    const subtitle = text || "";
    const loop = byId("pageSubtitle");
    const primaryText = byId("pageSubtitleText");
    const copyText = byId("pageSubtitleTextCopy");
    const accessibleText = byId("pageSubtitleAccessible");
    if (!loop || !primaryText || !copyText) return;

    const separator = ` ${String.fromCharCode(10022)} `;
    const marqueeText = Array(7).fill(`${separator}${subtitle}${separator}`).join(" ");
    primaryText.textContent = marqueeText;
    copyText.textContent = marqueeText;
    loop.setAttribute("aria-label", subtitle);
    if (accessibleText) accessibleText.textContent = subtitle;
    startCurvedSubtitleLoop();
}

function startCurvedSubtitleLoop() {
    if (state.curvedLoop.frameId) {
        cancelAnimationFrame(state.curvedLoop.frameId);
    }

    const primaryText = byId("pageSubtitleText");
    const copyText = byId("pageSubtitleTextCopy");
    if (!primaryText || !copyText) return;

    const reducedMotion = REDUCED_DECORATIVE_MOTION || window.matchMedia("(prefers-reduced-motion: reduce)").matches;
    if (reducedMotion) {
        primaryText.setAttribute("startOffset", "0%");
        copyText.setAttribute("startOffset", "-100%");
        state.curvedLoop.frameId = null;
        return;
    }

    state.curvedLoop.lastTime = performance.now();
    const speed = 0.0038;
    const tick = now => {
        const elapsed = Math.min(80, now - state.curvedLoop.lastTime);
        state.curvedLoop.lastTime = now;
        state.curvedLoop.offset = (state.curvedLoop.offset + elapsed * speed) % 100;
        primaryText.setAttribute("startOffset", `${state.curvedLoop.offset}%`);
        copyText.setAttribute("startOffset", `${state.curvedLoop.offset - 100}%`);
        state.curvedLoop.frameId = requestAnimationFrame(tick);
    };
    state.curvedLoop.frameId = requestAnimationFrame(tick);
}

function showSection(sectionName) {
    state.currentSection = sectionName;
    document.querySelectorAll(".section").forEach(section => {
        section.classList.toggle("active", section.id === sectionName);
    });
    document.querySelectorAll(".nav-item").forEach(item => {
        item.classList.toggle("active", item.dataset.section === sectionName);
    });

    const section = byId(sectionName);
    if (section) {
        byId("pageTitle").textContent = t(section.dataset.titleKey || "customerChat");
        setCurvedSubtitle(t(section.dataset.subtitleKey || "customerChatSubtitle"));
    }

    if (!isAdmin() && ADMIN_ONLY_SECTIONS.has(sectionName)) {
        renderAdminOnlySection(sectionName);
        return;
    }

    if (sectionName === "dashboard") loadDashboardStats();
    if (sectionName === "faq-management") loadFaqs();
    if (sectionName === "ai-learning") loadAiLearningCenter();
    if (sectionName === "chat-history") loadChatHistory();
    if (sectionName === "unanswered") loadUnansweredQuestions();
    if (sectionName === "categories") loadCategories();
    if (sectionName === "tickets") loadTickets();
    if (sectionName === "feedback") loadFeedback();
}

function renderAdminOnlySection(sectionName) {
    const message = t("adminOnlyPage");
    const sectionContainers = {
        dashboard: ["dashboardStats"],
        "ai-learning": ["pendingGeneratedFaqList", "generatedFaqList"],
        "chat-history": ["chatHistoryList"],
        unanswered: ["unansweredList"],
        feedback: ["feedbackSummary", "feedbackList"],
        "security-center": ["securityStats", "honeypotEventsList", "blockedIpsList", "auditLogsList"]
    };

    (sectionContainers[sectionName] || []).forEach(containerId => {
        if (byId(containerId)) {
            renderError(containerId, message);
        }
    });
}

function showToast(message, type = "info") {
    const toast = document.createElement("div");
    toast.className = `toast ${type}`;
    toast.textContent = message;
    byId("toastContainer").appendChild(toast);
    setTimeout(() => toast.remove(), 3600);
}

async function apiRequest(url, options = {}) {
    const fetchOptions = { ...options };
    const headers = { ...(fetchOptions.headers || {}) };
    if (fetchOptions.body && !(fetchOptions.body instanceof FormData)) {
        headers["Content-Type"] = headers["Content-Type"] || "application/json";
    }
    if (requiresCsrf(fetchOptions.method) && state.auth.csrfToken) {
        headers["X-CSRF-Token"] = state.auth.csrfToken;
    }
    fetchOptions.headers = headers;
    fetchOptions.credentials = "same-origin";

    const response = await fetch(url, fetchOptions);
    const text = await response.text();
    let data = null;
    if (text) {
        try {
            data = JSON.parse(text);
        } catch {
            data = text;
        }
    }
    if (response.status === 401) {
        redirectToLogin();
        throw new Error("Please log in first.");
    }
    if (!response.ok) {
        const message = typeof data === "object" && data ? data.message || data.error : data;
        throw new ApiRequestError(message || `Request failed with status ${response.status}`, response.status, data);
    }
    return data;
}

function isForbidden(error) {
    return Number(error?.status) === 403;
}

function adminOnlyMessage(error, fallbackKey = "unableToComplete") {
    return isForbidden(error) ? t("adminOnlyPage") : t(fallbackKey);
}

function requiresCsrf(method) {
    const normalized = (method || "GET").toUpperCase();
    return !["GET", "HEAD", "OPTIONS"].includes(normalized);
}

async function loadChatHistory() {
    renderLoading("chatHistoryList");
    try {
        let data;
        if (isAdmin()) {
            try {
                data = await apiRequest(api.dashboardHistory);
            } catch {
                data = await apiRequest(api.chatHistory);
            }
        } else {
            data = await apiRequest(api.chatHistory);
        }
        state.chatHistory = toArray(data, ["history", "items", "content"]);
        state.lastChatHistoryId = state.chatHistory[0]?.id || state.lastChatHistoryId;
        renderChatHistory(state.chatHistory);
    } catch (error) {
        console.error(error);
        renderError("chatHistoryList", adminOnlyMessage(error, "chatHistoryError"));
    }
}

function renderChatHistory(items) {
    const filtered = filterChatHistory(items);
    const container = byId("chatHistoryList");
    if (!filtered.length) {
        renderEmpty("chatHistoryList", t("noChatHistory"), t("firstConversationHint"));
        return;
    }
    container.innerHTML = filtered.map(item => {
        const question = item.userQuestion || item.question || item.message || "";
        const answer = item.aiResponse || item.answer || item.response || "";
        const answered = Boolean(item.answered);
        const mode = item.aiMode || item.mode || item.agentMode || "-";
        const language = item.language || "-";
        const inputType = item.inputType || "TEXT";
        const confidence = formatConfidence(item.confidenceScore ?? item.confidence);
        const supportUrl = item.whatsappSupportUrl || item.supportUrl;
        return `
            <article class="item-card">
                <div class="meta-row">
                    <span class="badge ${answered ? "answered" : "unanswered"}">${answered ? t("answered") : t("unanswered")}</span>
                    <span>${t("mode")}: ${escapeHtml(labelMode(mode))}</span>
                    <span>${t("language")}: ${escapeHtml(labelLanguage(language))}</span>
                    <span>${t("inputType")}: ${escapeHtml(inputType)}</span>
                </div>
                <p class="question">${escapeHtml(question)}</p>
                <p>${escapeHtml(answer || t("apiWaiting"))}</p>
                <div class="meta-row">
                    <span>${t("confidenceScore")}: ${confidence}</span>
                    <span>${t("createdDate")}: ${formatDate(item.createdAt || item.timestamp)}</span>
                </div>
                ${supportUrl ? `<div class="item-actions"><button class="button button-secondary button-small" type="button" onclick="openWhatsAppSupport(decodeURIComponent('${encodeForHandler(supportUrl)}'))">${t("contactHumanSupport")}</button></div>` : ""}
            </article>
        `;
    }).join("");
}

async function loadUnansweredQuestions() {
    if (!isAdmin()) {
        renderAdminOnlySection("unanswered");
        return;
    }
    renderLoading("unansweredList");
    try {
        const data = await apiRequest(api.unanswered);
        state.unanswered = toArray(data, ["unanswered", "items", "content"]);
        renderUnansweredQuestions(state.unanswered);
    } catch (error) {
        console.error(error);
        renderError("unansweredList", adminOnlyMessage(error, "unansweredError"));
    }
}

function renderUnansweredQuestions(items) {
    const filtered = filterUnanswered(items);
    const container = byId("unansweredList");
    if (!filtered.length) {
        renderEmpty("unansweredList", t("noUnanswered"), t("unansweredHint"));
        return;
    }
    container.innerHTML = filtered.map(item => {
        const status = item.status || "NEW";
        const question = item.question || item.userQuestion || "";
        return `
            <article class="item-card">
                <div class="meta-row">
                    <span class="badge ${statusClass(status)}">${escapeHtml(labelStatus(status))}</span>
                    <span>${t("language")}: ${escapeHtml(labelLanguage(item.language || "-"))}</span>
                    <span>${t("createdDate")}: ${formatDate(item.createdAt)}</span>
                </div>
                <p class="question">${escapeHtml(question)}</p>
                <div class="meta-row">
                    <span>${t("categoryGuess")}: ${escapeHtml(item.categoryGuess || "-")}</span>
                    <span>${t("resolvedDate")}: ${formatDate(item.resolvedAt || item.resolvedDate)}</span>
                </div>
                ${item.adminNote ? `<p>${t("adminNote")}: ${escapeHtml(item.adminNote)}</p>` : ""}
                <div class="item-actions">
                    <button class="button button-secondary button-small" type="button" onclick="markUnansweredReviewed(${Number(item.id)})">${t("markReviewed")}</button>
                    <button class="button button-secondary button-small" type="button" onclick="resolveUnanswered(${Number(item.id)})">${t("resolve")}</button>
                    <button class="button button-secondary button-small" type="button" onclick="convertUnansweredToFaq(${Number(item.id)})">${t("convertToFaq")}</button>
                    <button class="button button-secondary button-small" type="button" onclick="escalateUnansweredToTicket(${Number(item.id)})">${t("escalateToTicket")}</button>
                    <button class="button button-small" type="button" onclick="openWhatsAppSupport()">${t("contactHumanSupport")}</button>
                </div>
            </article>
        `;
    }).join("");
}

async function loadFaqs() {
    renderLoading("faqList");
    try {
        const data = await apiRequest(api.faqs);
        state.faqs = toArray(data, ["faqs", "items", "content"]);
        renderFaqs(state.faqs);
    } catch (error) {
        console.error(error);
        renderError("faqList", adminOnlyMessage(error, "faqError"));
    }
}

function renderFaqs(items) {
    const filtered = filterFaqs(items);
    const container = byId("faqList");
    if (!filtered.length) {
        renderEmpty("faqList", t("noFaqs"), t("faqAgentDescription"));
        return;
    }
    container.innerHTML = filtered.map(faq => `
        <article class="item-card">
            <div class="meta-row">
                <span class="badge ${statusClass(faq.status)}">${escapeHtml(labelStatus(faq.status || "ACTIVE"))}</span>
                <span>${t("category")}: ${escapeHtml(faq.category || "-")}</span>
                <span>${t("language")}: ${escapeHtml(labelLanguage(faq.language || "-"))}</span>
                <span>${t("priority")}: ${escapeHtml(faq.priority || "-")}</span>
            </div>
            <p class="question">${escapeHtml(faq.question || "")}</p>
            <p>${escapeHtml(faq.answer || "")}</p>
            ${faq.keywords ? `<p>${t("keywords")}: ${escapeHtml(faq.keywords)}</p>` : ""}
            <div class="item-actions">
                <button class="button button-secondary button-small" type="button" onclick="editFaq(${Number(faq.id)})">${t("edit")}</button>
                <button class="button button-danger button-small" type="button" onclick="deleteFaq(${Number(faq.id)})">${t("delete")}</button>
            </div>
        </article>
    `).join("");
}

async function loadAiLearningCenter() {
    if (!isAdmin()) {
        renderAdminOnlySection("ai-learning");
        return;
    }
    renderLoading("pendingGeneratedFaqList");
    renderLoading("generatedFaqList");
    try {
        const [pendingData, generatedData] = await Promise.all([
            apiRequest(`${api.faqs}/pending-review`),
            apiRequest(`${api.faqs}/generated`)
        ]);
        state.pendingGeneratedFaqs = toArray(pendingData, ["faqs", "items", "content"]);
        state.generatedFaqs = toArray(generatedData, ["faqs", "items", "content"]);
        renderGeneratedFaqs("pendingGeneratedFaqList", state.pendingGeneratedFaqs, true);
        renderGeneratedFaqs("generatedFaqList", state.generatedFaqs, false);
    } catch (error) {
        console.error(error);
        renderError("pendingGeneratedFaqList", adminOnlyMessage(error, "faqError"));
        renderError("generatedFaqList", adminOnlyMessage(error, "faqError"));
    }
}

function renderGeneratedFaqs(containerId, items, reviewActions) {
    const container = byId(containerId);
    if (!items.length) {
        renderEmpty(containerId, t("noData"), t("aiLearningSubtitle"));
        return;
    }
    container.innerHTML = items.map(faq => `
        <article class="item-card">
            <div class="meta-row">
                <span class="badge ${statusClass(faq.status)}">${escapeHtml(labelStatus(faq.status || "DRAFT"))}</span>
                <span>${t("category")}: ${escapeHtml(faq.category || "-")}</span>
                <span>${t("language")}: ${escapeHtml(labelLanguage(faq.language || "-"))}</span>
                <span>${t("createdDate")}: ${formatDate(faq.createdAt)}</span>
            </div>
            <p class="question">${escapeHtml(faq.question || "")}</p>
            <p>${escapeHtml(faq.answer || "")}</p>
            ${faq.generatedFromQuestion ? `<p>${t("question")}: ${escapeHtml(faq.generatedFromQuestion)}</p>` : ""}
            ${reviewActions ? `
                <div class="item-actions">
                    <button class="button button-small" type="button" onclick="approveGeneratedFaq(${Number(faq.id)})">${t("approve")}</button>
                    <button class="button button-danger button-small" type="button" onclick="rejectGeneratedFaq(${Number(faq.id)})">${t("reject")}</button>
                </div>
            ` : ""}
        </article>
    `).join("");
}

async function approveGeneratedFaq(id) {
    try {
        await apiRequest(`${api.faqs}/${encodeURIComponent(id)}/approve`, { method: "PUT" });
        showToast(t("saved"), "success");
        await Promise.allSettled([loadAiLearningCenter(), loadFaqs(), loadDashboardStats()]);
    } catch (error) {
        console.error(error);
        showToast(error.message || t("unableToComplete"), "error");
    }
}

async function rejectGeneratedFaq(id) {
    try {
        await apiRequest(`${api.faqs}/${encodeURIComponent(id)}/reject`, { method: "PUT" });
        showToast(t("saved"), "success");
        await Promise.allSettled([loadAiLearningCenter(), loadFaqs(), loadDashboardStats()]);
    } catch (error) {
        console.error(error);
        showToast(error.message || t("unableToComplete"), "error");
    }
}

async function loadDashboardStats() {
    if (!isAdmin()) {
        renderAdminOnlySection("dashboard");
        return;
    }
    renderLoading("dashboardStats");
    try {
        const stats = await apiRequest(api.stats);
        renderDashboardStats(stats || {});
    } catch (error) {
        console.error(error);
        renderDashboardStats(defaultStats(), adminOnlyMessage(error, "dashboardStatsUnavailable"));
    }
}

function renderDashboardStats(stats, warningMessage = "") {
    const container = byId("dashboardStats");
    const finalWarning = !isAdmin() ? t("adminOnlyPage") : warningMessage;
    const cards = [
        ["totalFaqs", "totalFaqs"],
        ["activeFaqs", "activeFaqs"],
        ["totalQuestions", "totalQuestions"],
        ["answeredQuestions", "answeredQuestions"],
        ["unansweredQuestions", "unansweredQuestionsStat"],
        ["averageConfidenceScore", "averageConfidence"],
        ["openTickets", "openTickets"],
        ["resolvedTickets", "resolvedTickets"],
        ["helpfulFeedback", "helpfulFeedback"],
        ["unhelpfulFeedback", "unhelpfulFeedback"]
    ];
    container.innerHTML = `
        ${finalWarning ? `<div class="error-state wide"><p>${escapeHtml(finalWarning)}</p></div>` : ""}
        ${cards.map(([key, labelKey]) => `
            <div class="stat-card">
                <span>${t(labelKey)}</span>
                <strong>${formatStatValue(key, stats[key])}</strong>
            </div>
        `).join("")}
    `;
}

async function askQuestion(event) {
    event.preventDefault();
    const question = byId("chatInput").value.trim();
    if (!question) return;

    const mode = byId("agentModeSelect").value;
    const language = byId("chatLanguageSelect").value;
    const endpoint = mode === "REAL_AI" ? api.aiAsk : api.chatAsk;
    const inputType = state.voice.inputFromVoice ? "VOICE" : "TEXT";
    const payload = { question, language, mode, sessionId: getSessionId(), inputType };

    appendChatBubble("user", question);
    byId("chatInput").value = "";
    state.voice.inputFromVoice = false;
    setVoiceStatus(t("voiceIdle"));
    const loadingId = appendChatLoading();

    try {
        const response = await apiRequest(endpoint, {
            method: "POST",
            body: JSON.stringify(payload)
        });
        removeElement(loadingId);
        appendBotResponse(response || {}, mode, language);
        updateTokenUsageFromResponse(response || {});
        await loadTokenUsage(true);
        await Promise.allSettled([loadChatHistory(), loadUnansweredQuestions(), loadDashboardStats()]);
    } catch (error) {
        console.error(error);
        removeElement(loadingId);
        appendChatBubble("bot", error.message || t("unableToComplete"));
        showToast(error.message || t("unableToComplete"), "error");
    }
}

async function loadTokenUsage(preserveLastStatus = false) {
    try {
        const usage = await apiRequest(`${api.aiTokenUsage}?sessionId=${encodeURIComponent(getSessionId())}`);
        renderTokenUsage(usage || {}, { preserveLastStatus });
    } catch (error) {
        console.error(error);
        renderTokenUsage(state.tokenUsage, { preserveLastStatus });
    }
}

function updateTokenUsageFromResponse(response) {
    if (typeof response.tokensUsed === "number" || typeof response.tokenLimit === "number") {
        renderTokenUsage({
            tokensUsed: response.tokensUsed,
            tokenLimit: response.tokenLimit,
            tokensRemaining: response.tokensRemaining,
            percentUsed: response.tokenPercentUsed,
            lastRequestTokens: response.lastRequestTokens,
            openAiUsed: response.openAiUsed,
            tokenUsageReason: response.tokenUsageReason
        });
    }
}

function renderTokenUsage(usage, options = {}) {
    const previous = state.tokenUsage || {};
    const tokenLimit = Number(usage.tokenLimit ?? previous.tokenLimit ?? 0);
    const tokensUsed = Math.max(0, Number(usage.tokensUsed || 0));
    const unlimited = tokenLimit <= 0;
    const tokensRemaining = Math.max(0, Number(
        unlimited ? Number.MAX_SAFE_INTEGER : (usage.tokensRemaining ?? (tokenLimit - tokensUsed))
    ));
    const percentUsed = Math.min(100, Math.max(0, Number(
        unlimited ? 0 : (usage.percentUsed ?? usage.tokenPercentUsed ?? ((tokensUsed / Math.max(1, tokenLimit)) * 100))
    )));
    const lastRequestTokens = options.preserveLastStatus
        ? Number(previous.lastRequestTokens || 0)
        : Math.max(0, Number(usage.lastRequestTokens || 0));
    const openAiUsed = options.preserveLastStatus
        ? Boolean(previous.openAiUsed)
        : Boolean(usage.openAiUsed);
    const tokenUsageReason = options.preserveLastStatus
        ? previous.tokenUsageReason
        : usage.tokenUsageReason;

    state.tokenUsage = { tokensUsed, tokenLimit, tokensRemaining, percentUsed, lastRequestTokens, openAiUsed, tokenUsageReason };
    animateCount("tokenUsedCount", tokensUsed);
    byId("tokenLimitCount").textContent = unlimited ? "Unlimited" : formatNumber(tokenLimit);
    byId("tokenRemainingCount").textContent = unlimited ? "Unlimited" : formatNumber(tokensRemaining);
    byId("tokenMeterFill").style.width = `${percentUsed}%`;
    byId("tokenMeterFill").classList.toggle("near-limit", percentUsed >= 85);
    renderLastTokenUsage(lastRequestTokens, openAiUsed, tokenUsageReason);
}

function renderLastTokenUsage(lastRequestTokens, openAiUsed, tokenUsageReason) {
    const statusElement = byId("tokenLastUsageStatus");
    if (!statusElement) return;
    const reason = tokenUsageReason || "No AI request yet.";
    const prefix = openAiUsed
        ? `Last request: ${formatNumber(lastRequestTokens)} tokens.`
        : "Last request: 0 API tokens.";
    statusElement.textContent = `${prefix} ${reason}`;
    statusElement.classList.toggle("used", Boolean(openAiUsed));
}

function animateCount(elementId, targetValue, duration = 900) {
    const element = byId(elementId);
    if (!element) return;
    const fromValue = Number(String(element.textContent || "0").replaceAll(",", "")) || 0;
    const toValue = Number(targetValue || 0);
    const start = performance.now();

    function tick(now) {
        const progress = Math.min(1, (now - start) / duration);
        const eased = 1 - Math.pow(1 - progress, 3);
        const current = fromValue + (toValue - fromValue) * eased;
        element.textContent = formatNumber(Math.round(current));
        if (progress < 1) {
            requestAnimationFrame(tick);
        }
    }

    requestAnimationFrame(tick);
}

async function sendFeedback(helpful = true) {
    try {
        await apiRequest(api.feedback, {
            method: "POST",
            body: JSON.stringify({
                helpful,
                chatHistoryId: state.lastChatHistoryId,
                comment: helpful ? "Helpful answer" : "Needs improvement"
            })
        });
        showToast(t("feedbackSaved"), "success");
        await Promise.allSettled([loadFeedback(), loadDashboardStats()]);
    } catch (error) {
        console.error(error);
        showToast(error.message || t("unableToComplete"), "error");
    }
}

function openWhatsAppSupport(url = WHATSAPP_URL) {
    window.open(url || WHATSAPP_URL, "_blank");
    showToast(t("supportOpened"), "success");
}

async function clearChatHistory() {
    try {
        await apiRequest(api.chatHistory, { method: "DELETE" });
        state.chatHistory = [];
        renderChatHistory([]);
        showToast(t("clearHistory"), "success");
    } catch (error) {
        console.error(error);
        showToast(t("clearHistoryUnavailable"), "warning");
    }
}

async function saveFaq(event) {
    event.preventDefault();
    const id = byId("faqId").value;
    const payload = {
        question: value("faqQuestion"),
        answer: value("faqAnswer"),
        category: value("faqCategory"),
        status: value("faqStatus"),
        language: value("faqLanguage"),
        priority: value("faqPriority"),
        keywords: value("faqKeywords")
    };
    try {
        await apiRequest(id ? `${api.faqs}/${encodeURIComponent(id)}` : api.faqs, {
            method: id ? "PUT" : "POST",
            body: JSON.stringify(payload)
        });
        resetFaqForm();
        showToast(t("saved"), "success");
        await Promise.allSettled([loadFaqs(), loadDashboardStats()]);
    } catch (error) {
        console.error(error);
        showToast(error.message || t("unableToComplete"), "error");
    }
}

function editFaq(id) {
    const faq = state.faqs.find(item => Number(item.id) === Number(id));
    if (!faq) return;
    setValue("faqId", faq.id);
    setValue("faqQuestion", faq.question);
    setValue("faqAnswer", faq.answer);
    setValue("faqCategory", faq.category);
    setValue("faqStatus", faq.status || "ACTIVE");
    setValue("faqLanguage", faq.language || "EN");
    setValue("faqPriority", faq.priority || "NORMAL");
    setValue("faqKeywords", faq.keywords);
    showSection("faq-management");
}

async function deleteFaq(id) {
    if (!confirm(`${t("deleteFaq")}?`)) return;
    try {
        await apiRequest(`${api.faqs}/${encodeURIComponent(id)}`, { method: "DELETE" });
        showToast(t("deleted"), "success");
        await Promise.allSettled([loadFaqs(), loadDashboardStats()]);
    } catch (error) {
        console.error(error);
        showToast(error.message || t("unableToComplete"), "error");
    }
}

function resetFaqForm() {
    byId("faqForm").reset();
    setValue("faqId", "");
}

async function markUnansweredReviewed(id) {
    await updateUnanswered(`${api.unanswered}/${encodeURIComponent(id)}/review`, "PUT");
}

async function resolveUnanswered(id) {
    await updateUnanswered(`${api.unanswered}/${encodeURIComponent(id)}/resolve`, "PUT");
}

async function escalateUnansweredToTicket(id) {
    await updateUnanswered(`${api.unanswered}/${encodeURIComponent(id)}/ticket`, "POST");
}

async function convertUnansweredToFaq(id) {
    const answer = prompt(t("answer"));
    if (!answer) return;
    try {
        await apiRequest(`${api.unanswered}/${encodeURIComponent(id)}/create-faq`, {
            method: "POST",
            body: JSON.stringify({ answer, status: "ACTIVE", priority: "NORMAL" })
        });
        showToast(t("saved"), "success");
        await Promise.allSettled([loadUnansweredQuestions(), loadFaqs(), loadDashboardStats()]);
    } catch (error) {
        console.error(error);
        showToast(error.message || t("unableToComplete"), "error");
    }
}

async function updateUnanswered(url, method) {
    try {
        await apiRequest(url, { method });
        showToast(t("saved"), "success");
        await Promise.allSettled([loadUnansweredQuestions(), loadTickets(), loadDashboardStats()]);
    } catch (error) {
        console.error(error);
        showToast(error.message || t("unableToComplete"), "error");
    }
}

async function loadCategories() {
    renderLoading("categoryList");
    try {
        const data = await apiRequest(api.categories);
        state.categories = toArray(data, ["categories", "items", "content"]);
        const container = byId("categoryList");
        if (!state.categories.length) {
            renderEmpty("categoryList", t("noCategories"), t("apiWaiting"));
            return;
        }
        container.innerHTML = state.categories.map(category => `
            <article class="item-card">
                <h3>${escapeHtml(category.nameEnglish || category.name || "-")}</h3>
                <p>${escapeHtml(category.description || "")}</p>
                <div class="meta-row">
                    <span>${t("nameArabic")}: ${escapeHtml(category.nameArabic || "-")}</span>
                    <span class="badge ${statusClass(category.status)}">${escapeHtml(category.status || "ACTIVE")}</span>
                </div>
            </article>
        `).join("");
    } catch (error) {
        console.error(error);
        renderError("categoryList", t("apiWaiting"));
    }
}

async function saveCategory(event) {
    event.preventDefault();
    const id = value("categoryId");
    const payload = {
        nameEnglish: value("categoryNameEnglish"),
        nameArabic: value("categoryNameArabic"),
        description: value("categoryDescription"),
        status: value("categoryStatus") || "ACTIVE"
    };
    try {
        await apiRequest(id ? `${api.categories}/${encodeURIComponent(id)}` : api.categories, {
            method: id ? "PUT" : "POST",
            body: JSON.stringify(payload)
        });
        byId("categoryForm").reset();
        setValue("categoryId", "");
        showToast(t("saved"), "success");
        await loadCategories();
    } catch (error) {
        console.error(error);
        showToast(error.message || t("unableToComplete"), "error");
    }
}

async function saveTicket(event) {
    event.preventDefault();
    const payload = {
        customerName: value("ticketCustomerName"),
        customerEmail: value("ticketCustomerEmail"),
        subject: value("ticketSubject"),
        message: value("ticketMessage"),
        priority: value("ticketPriority")
    };
    try {
        await apiRequest(api.tickets, { method: "POST", body: JSON.stringify(payload) });
        byId("ticketForm").reset();
        showToast(t("saved"), "success");
        await Promise.allSettled([loadTickets(), loadDashboardStats()]);
    } catch (error) {
        console.error(error);
        showToast(error.message || t("unableToComplete"), "error");
    }
}

async function loadTickets() {
    renderLoading("ticketsList");
    try {
        const data = await apiRequest(api.tickets);
        state.tickets = toArray(data, ["tickets", "items", "content"]);
        const container = byId("ticketsList");
        if (!state.tickets.length) {
            renderEmpty("ticketsList", t("noTickets"), t("apiWaiting"));
            return;
        }
        container.innerHTML = state.tickets.map(ticket => `
            <article class="item-card">
                <div class="meta-row">
                    <span class="badge ${statusClass(ticket.status)}">${escapeHtml(ticket.status || "-")}</span>
                    <span>${t("priority")}: ${escapeHtml(ticket.priority || "-")}</span>
                    <span>${t("createdDate")}: ${formatDate(ticket.createdAt)}</span>
                </div>
                <h3>${escapeHtml(ticket.subject || t("supportTickets"))}</h3>
                <p>${escapeHtml(ticket.message || "")}</p>
                <div class="meta-row">
                    <span>${t("customerName")}: ${escapeHtml(ticket.customerName || "-")}</span>
                    <span>${t("customerEmail")}: ${escapeHtml(ticket.customerEmail || "-")}</span>
                </div>
            </article>
        `).join("");
    } catch (error) {
        console.error(error);
        renderError("ticketsList", adminOnlyMessage(error, "apiWaiting"));
    }
}

async function loadFeedback() {
    if (!isAdmin()) {
        renderAdminOnlySection("feedback");
        return;
    }
    renderLoading("feedbackList");
    try {
        const [summary, feedback] = await Promise.all([
            apiRequest(`${api.feedback}/summary`),
            apiRequest(api.feedback)
        ]);
        state.feedback = toArray(feedback, ["feedback", "items", "content"]);
        renderFeedbackSummary(summary || {});
        if (!state.feedback.length) {
            renderEmpty("feedbackList", t("noFeedback"), t("apiWaiting"));
            return;
        }
        byId("feedbackList").innerHTML = state.feedback.map(item => `
            <article class="item-card">
                <div class="meta-row">
                    <span class="badge ${item.helpful ? "success" : "warning"}">${item.helpful ? t("helpfulFeedback") : t("unhelpfulFeedback")}</span>
                    <span>${t("createdDate")}: ${formatDate(item.createdAt)}</span>
                </div>
                <p>${escapeHtml(item.comment || "")}</p>
            </article>
        `).join("");
    } catch (error) {
        console.error(error);
        renderFeedbackSummary(isForbidden(error) ? null : {});
        renderError("feedbackList", adminOnlyMessage(error, "apiWaiting"));
    }
}

function renderFeedbackSummary(summary) {
    if (!summary) {
        renderError("feedbackSummary", t("adminOnlyPage"));
        return;
    }
    byId("feedbackSummary").innerHTML = `
        <div class="stat-card"><span>${t("helpfulFeedback")}</span><strong>${Number(summary.helpfulFeedback || summary.helpful || 0)}</strong></div>
        <div class="stat-card"><span>${t("unhelpfulFeedback")}</span><strong>${Number(summary.unhelpfulFeedback || summary.unhelpful || 0)}</strong></div>
    `;
}

async function loadSecurityCenter() {
    if (!isAdmin()) {
        renderAdminOnlySection("security-center");
        return;
    }
    renderLoading("securityStats");
    renderLoading("honeypotEventsList");
    renderLoading("blockedIpsList");
    renderLoading("auditLogsList");
    try {
        const [stats, honeypotEvents, blockedIps, auditLogs] = await Promise.all([
            apiRequest(api.securityStats),
            apiRequest(api.honeypotEvents),
            apiRequest(api.blockedIps),
            apiRequest(api.auditLogs)
        ]);
        state.securityStats = stats || {};
        state.honeypotEvents = toArray(honeypotEvents, ["items", "content"]);
        state.blockedIps = toArray(blockedIps, ["items", "content"]);
        state.auditLogs = toArray(auditLogs, ["items", "content"]);
        renderSecurityStats(state.securityStats);
        renderHoneypotEvents(state.honeypotEvents);
        renderBlockedIps(state.blockedIps);
        renderAuditLogs(state.auditLogs);
    } catch (error) {
        console.error(error);
        const message = adminOnlyMessage(error, "unableToComplete");
        renderError("securityStats", message);
        renderError("honeypotEventsList", message);
        renderError("blockedIpsList", message);
        renderError("auditLogsList", message);
    }
}

function renderSecurityStats(stats) {
    const cards = [
        ["totalAuditLogs", "auditLogs"],
        ["criticalEvents", "criticalEvents"],
        ["highEvents", "highEvents"],
        ["honeypotTriggers", "honeypotEvents"],
        ["activeBlockedIps", "blockedIps"],
        ["rateLimitEvents", "rateLimitEvents"],
        ["promptInjectionAttempts", "promptInjectionAttempts"]
    ];
    byId("securityStats").innerHTML = cards.map(([key, label]) => `
        <div class="stat-card">
            <span>${t(label)}</span>
            <strong>${Number(stats[key] || 0)}</strong>
        </div>
    `).join("");
}

function renderHoneypotEvents(items) {
    if (!items.length) {
        renderEmpty("honeypotEventsList", t("noData"), t("honeypotExplanation"));
        return;
    }
    byId("honeypotEventsList").innerHTML = items.map(item => `
        <article class="item-card security-item">
            <div class="meta-row">
                <span class="badge danger">${escapeHtml(item.blocked ? "BLOCKED" : "LOGGED")}</span>
                <span>${t("ipAddress")}: ${escapeHtml(item.ipAddress || "-")}</span>
                <span>${t("createdDate")}: ${formatDate(item.createdAt)}</span>
            </div>
            <p class="question">${escapeHtml(item.requestPath || "-")}</p>
            <p>${t("userAgent")}: ${escapeHtml(item.userAgent || "-")}</p>
            <div class="meta-row">
                <span>${t("inputType")}: ${escapeHtml(item.httpMethod || "-")}</span>
                <span>${t("details")}: ${escapeHtml(item.reason || "-")}</span>
            </div>
        </article>
    `).join("");
}

function renderBlockedIps(items) {
    if (!items.length) {
        renderEmpty("blockedIpsList", t("noData"), t("apiWaiting"));
        return;
    }
    byId("blockedIpsList").innerHTML = items.map(item => `
        <article class="item-card security-item">
            <div class="meta-row">
                <span class="badge ${item.active ? "danger" : "success"}">${escapeHtml(item.active ? "ACTIVE" : "INACTIVE")}</span>
                <span>${t("ipAddress")}: ${escapeHtml(item.ipAddress || "-")}</span>
                <span>${t("createdDate")}: ${formatDate(item.blockedAt)}</span>
                <span>${t("expiresDate")}: ${formatDate(item.expiresAt)}</span>
            </div>
            <p>${escapeHtml(item.reason || "-")}</p>
            ${item.active ? `<div class="item-actions"><button class="button button-secondary button-small" type="button" onclick="unblockIp(${Number(item.id)})">${t("unblock")}</button></div>` : ""}
        </article>
    `).join("");
}

function renderAuditLogs(items) {
    if (!items.length) {
        renderEmpty("auditLogsList", t("noData"), t("apiWaiting"));
        return;
    }
    byId("auditLogsList").innerHTML = items.slice(0, 80).map(item => `
        <article class="item-card security-item">
            <div class="meta-row">
                <span class="badge ${severityClass(item.severity)}">${escapeHtml(item.severity || "LOW")}</span>
                <span>${t("eventType")}: ${escapeHtml(item.eventType || "-")}</span>
                <span>${t("ipAddress")}: ${escapeHtml(item.ipAddress || "-")}</span>
                <span>${t("createdDate")}: ${formatDate(item.createdAt)}</span>
            </div>
            <p class="question">${escapeHtml(item.requestPath || "-")}</p>
            <p>${escapeHtml(item.details || "-")}</p>
        </article>
    `).join("");
}

async function unblockIp(id) {
    try {
        await apiRequest(`${api.blockedIps}/${encodeURIComponent(id)}/unblock`, { method: "PUT" });
        showToast(t("saved"), "success");
        await loadSecurityCenter();
    } catch (error) {
        console.error(error);
        showToast(error.message || t("unableToComplete"), "error");
    }
}

async function loadAiModes() {
    renderLoading("aiModesList");
    try {
        const data = await apiRequest(api.aiModes);
        const modes = toArray(data, ["modes", "items"]);
        if (!modes.length) {
            renderEmpty("aiModesList", t("noData"), t("apiWaiting"));
            return;
        }
        byId("aiModesList").innerHTML = modes.map(mode => `
            <article class="item-card">
                <h3>${escapeHtml(mode.name || mode.mode || t("realAiAgent"))}</h3>
                <p>${escapeHtml(mode.description || "")}</p>
                <div class="meta-row"><span>${escapeHtml(mode.mode || "")}</span></div>
            </article>
        `).join("");
    } catch (error) {
        console.error(error);
        renderError("aiModesList", adminOnlyMessage(error, "apiWaiting"));
    }
}

function seedChatWelcome() {
    byId("chatMessages").innerHTML = `
        <div class="bubble bot">
            <p>${state.language === "AR"
        ? "مرحبا، اسألني أي سؤال. استخدم وكيل الذكاء الاصطناعي للمحادثة العامة، أو وكيل قاعدة الأسئلة للإجابات المحفوظة فقط."
        : "Hi, ask me anything. Use Real AI for general chat, or FAQ Database Agent for saved support answers only."}</p>
        </div>
    `;
}

function appendChatBubble(type, text) {
    const bubble = document.createElement("div");
    bubble.className = `bubble ${type}`;
    bubble.textContent = text;
    byId("chatMessages").appendChild(bubble);
    scrollChat();
}

function appendVoiceBubble(audioUrl, transcript = "") {
    const id = `voice-${Date.now()}`;
    const bubble = document.createElement("div");
    bubble.id = id;
    bubble.className = "bubble user voice-message";
    bubble.innerHTML = `
        <div class="voice-message-head">
            <span class="voice-dot" aria-hidden="true"></span>
            <strong>Voice message</strong>
        </div>
        <audio controls src="${audioUrl}"></audio>
        <p class="voice-transcript">${transcript ? escapeHtml(transcript) : "Transcribing..."}</p>
    `;
    byId("chatMessages").appendChild(bubble);
    scrollChat();
    return id;
}

function updateVoiceBubbleTranscript(id, transcript = "") {
    const bubble = byId(id);
    const transcriptElement = bubble?.querySelector(".voice-transcript");
    if (transcriptElement && transcript) {
        transcriptElement.textContent = transcript;
    }
}

function appendChatLoading() {
    const id = `loading-${Date.now()}`;
    const bubble = document.createElement("div");
    bubble.id = id;
    bubble.className = "bubble bot loading";
    bubble.innerHTML = `
        <span>${t("typing")}</span>
        <span class="typing-dots"><span></span><span></span><span></span></span>
    `;
    byId("chatMessages").appendChild(bubble);
    scrollChat();
    return id;
}

function appendBotResponse(response, fallbackMode, fallbackLanguage) {
    const answer = response.answer || response.aiResponse || response.response || t("apiWaiting");
    const answered = Boolean(response.answered);
    const mode = response.mode || fallbackMode;
    const supportUrl = response.whatsappSupportUrl || response.supportUrl || (!answered ? WHATSAPP_URL : "");
    state.lastChatHistoryId = response.chatHistoryId || response.historyId || state.lastChatHistoryId;

    const bubble = document.createElement("div");
    bubble.className = "bubble bot";
    bubble.innerHTML = `
        <p>${escapeHtml(answer)}</p>
        ${response.audioBase64 ? `<audio class="bot-audio-reply" controls src="data:audio/mpeg;base64,${response.audioBase64}"></audio>` : ""}
        ${response.generatedFaqSaved ? `<div class="learning-note">${escapeHtml(response.learningMessage || t("aiLearningSaved"))}</div>` : ""}
        ${renderSuggestedQuestions(response.suggestedQuestions)}
        <div class="item-actions">
            <span>${t("answerHelpful")}</span>
            <button class="button button-secondary button-small" type="button" onclick="sendFeedback(true)">${t("yes")}</button>
            <button class="button button-secondary button-small" type="button" onclick="sendFeedback(false)">${t("no")}</button>
            ${supportUrl && !answered ? `<button class="button button-small" type="button" onclick="openWhatsAppSupport(decodeURIComponent('${encodeForHandler(supportUrl)}'))">${t("contactHumanSupport")}</button>` : ""}
            ${!answered ? `<button class="button button-secondary button-small" type="button" onclick="prefillTicket(decodeURIComponent('${encodeForHandler(answer)}'))">${t("createTicket")}</button>` : ""}
        </div>
    `;
    byId("chatMessages").appendChild(bubble);
    scrollChat();
}

function renderSuggestedQuestions(questions) {
    const list = Array.isArray(questions) ? questions.filter(Boolean) : [];
    if (!list.length) return "";
    return `
        <div>
            <strong>${t("suggestedQuestions")}</strong>
            <ul>${list.map(question => `<li>${escapeHtml(question)}</li>`).join("")}</ul>
        </div>
    `;
}

function prefillTicket(message = "") {
    showSection("tickets");
    setValue("ticketSubject", t("contactHumanSupport"));
    setValue("ticketMessage", message || value("chatInput"));
}

function renderLoading(containerId) {
    byId(containerId).innerHTML = `
        <div class="loading-state">
            <div>
                <div class="spinner"></div>
                <p>${t("loading")}...</p>
            </div>
        </div>
    `;
}

function renderEmpty(containerId, title, message = "") {
    byId(containerId).innerHTML = `
        <div class="empty-state">
            <div>
                <h3>${escapeHtml(title)}</h3>
                ${message ? `<p>${escapeHtml(message)}</p>` : ""}
            </div>
        </div>
    `;
}

function renderError(containerId, message) {
    const finalMessage = !isAdmin() && ADMIN_ONLY_CONTAINERS.has(containerId)
        ? t("adminOnlyPage")
        : message;
    byId(containerId).innerHTML = `
        <div class="error-state">
            <div>
                <h3>${escapeHtml(finalMessage)}</h3>
            </div>
        </div>
    `;
}

function filterChatHistory(items) {
    const search = value("historySearchInput").toLowerCase();
    const mode = value("historyModeFilter");
    const status = value("historyStatusFilter");
    const language = value("historyLanguageFilter");
    return items.filter(item => {
        const haystack = `${item.userQuestion || item.question || ""} ${item.aiResponse || item.answer || ""}`.toLowerCase();
        const itemMode = item.aiMode || item.mode || item.agentMode || "";
        const itemLanguage = item.language || "";
        const answered = Boolean(item.answered);
        return (!search || haystack.includes(search))
            && (!mode || itemMode === mode)
            && (!language || itemLanguage === language)
            && (!status || (status === "answered" ? answered : !answered));
    });
}

function filterUnanswered(items) {
    const search = value("unansweredSearchInput").toLowerCase();
    const status = value("unansweredStatusFilter");
    const language = value("unansweredLanguageFilter");
    return items.filter(item => {
        const haystack = `${item.question || item.userQuestion || ""} ${item.adminNote || ""}`.toLowerCase();
        return (!search || haystack.includes(search))
            && (!status || item.status === status)
            && (!language || item.language === language);
    });
}

function filterFaqs(items) {
    const search = value("faqSearchInput").toLowerCase();
    const category = value("faqCategoryFilter").toLowerCase();
    const status = value("faqStatusFilter");
    const language = value("faqLanguageFilter");
    return items.filter(item => {
        const haystack = `${item.question || ""} ${item.answer || ""} ${item.keywords || ""}`.toLowerCase();
        const itemCategory = String(item.category || "").toLowerCase();
        return (!search || haystack.includes(search))
            && (!category || itemCategory.includes(category))
            && (!status || item.status === status)
            && (!language || item.language === language);
    });
}

function toArray(data, keys = []) {
    if (Array.isArray(data)) return data;
    if (!data || typeof data !== "object") return [];
    for (const key of keys) {
        if (Array.isArray(data[key])) return data[key];
    }
    return [];
}

function defaultStats() {
    return {
        totalFaqs: 0,
        activeFaqs: 0,
        totalQuestions: 0,
        answeredQuestions: 0,
        unansweredQuestions: 0,
        averageConfidenceScore: 0,
        openTickets: 0,
        resolvedTickets: 0,
        helpfulFeedback: 0,
        unhelpfulFeedback: 0
    };
}

function formatStatValue(key, valueToFormat) {
    if (key === "averageConfidenceScore") {
        return formatConfidence(valueToFormat);
    }
    return formatNumber(valueToFormat);
}

function formatNumber(valueToFormat) {
    return Number(valueToFormat || 0).toLocaleString();
}

function formatConfidence(valueToFormat) {
    const number = Number(valueToFormat || 0);
    if (number <= 1) return `${Math.round(number * 100)}%`;
    return `${Math.round(number)}%`;
}

function formatDate(valueToFormat) {
    if (!valueToFormat) return "-";
    const date = new Date(valueToFormat);
    if (Number.isNaN(date.getTime())) return String(valueToFormat);
    return date.toLocaleString(state.language === "AR" ? "ar-SA" : "en-US");
}

function labelMode(mode) {
    if (mode === "REAL_AI") return t("realAiAgent");
    if (mode === "FAQ_ONLY") return t("faqDatabaseAgent");
    return mode || "-";
}

function labelLanguage(language) {
    if (language === "AR") return t("arabic");
    if (language === "EN") return t("english");
    return language || "-";
}

function labelStatus(status = "") {
    const normalized = String(status).toUpperCase();
    const statusMap = {
        ACTIVE: t("active"),
        INACTIVE: t("inactive"),
        DRAFT: t("draft"),
        ARCHIVED: t("archived"),
        NEW: t("newStatus"),
        REVIEWED: t("reviewed"),
        RESOLVED: t("resolved"),
        ESCALATED: t("escalated"),
        CONVERTED_TO_FAQ: t("convertedToFaq")
    };
    return statusMap[normalized] || status || "-";
}

function statusClass(status = "") {
    const normalized = String(status).toLowerCase();
    if (["active", "answered", "resolved"].includes(normalized)) return "success";
    if (["draft", "reviewed", "new"].includes(normalized)) return "warning";
    if (["inactive", "unanswered"].includes(normalized)) return "danger";
    if (["escalated", "converted_to_faq", "archived"].includes(normalized)) return "info";
    return "";
}

function severityClass(severity = "") {
    const normalized = String(severity).toUpperCase();
    if (normalized === "CRITICAL" || normalized === "HIGH") return "danger";
    if (normalized === "MEDIUM") return "warning";
    return "success";
}

function getSessionId() {
    let sessionId = localStorage.getItem("faqAgentSessionId");
    if (!sessionId) {
        sessionId = crypto.randomUUID ? crypto.randomUUID() : `session-${Date.now()}`;
        localStorage.setItem("faqAgentSessionId", sessionId);
    }
    return sessionId;
}

function scrollChat() {
    const chat = byId("chatMessages");
    chat.scrollTop = chat.scrollHeight;
}

function removeElement(id) {
    const element = byId(id);
    if (element) element.remove();
}

function t(key) {
    return translations[state.language]?.[key] || translations.EN[key] || key;
}

function byId(id) {
    return document.getElementById(id);
}

function value(id) {
    return byId(id)?.value || "";
}

function setValue(id, valueToSet) {
    const element = byId(id);
    if (element) element.value = valueToSet || "";
}

function escapeHtml(valueToEscape) {
    return String(valueToEscape ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

function encodeForHandler(valueToEncode) {
    return encodeURIComponent(String(valueToEncode ?? ""));
}


function initRushPageScene() {
    const scene = byId("rushPageScene");
    const depthItems = document.querySelectorAll("[data-rush-depth]");
    if (!scene && depthItems.length === 0) return;

    let targetX = 0;
    let targetY = 0;
    let currentX = 0;
    let currentY = 0;
    let ticking = false;

    const applyMotion = () => {
        currentX += (targetX - currentX) * 0.07;
        currentY += (targetY - currentY) * 0.07;

        const rotateX = `${(-currentY * 6).toFixed(3)}deg`;
        const rotateY = `${(currentX * 8).toFixed(3)}deg`;
        const moveX = `${(currentX * 20).toFixed(3)}px`;
        const moveY = `${(currentY * 14).toFixed(3)}px`;

        if (scene) {
            scene.style.setProperty("--rush-rotate-x", rotateX);
            scene.style.setProperty("--rush-rotate-y", rotateY);
            scene.style.setProperty("--rush-move-x", moveX);
            scene.style.setProperty("--rush-move-y", moveY);
        }

        depthItems.forEach(item => {
            const depth = Number(item.dataset.rushDepth || 12);
            item.style.setProperty("--rush-rotate-x", rotateX);
            item.style.setProperty("--rush-rotate-y", rotateY);
            item.style.setProperty("--rush-move-x", `${(currentX * depth).toFixed(3)}px`);
            item.style.setProperty("--rush-move-y", `${(currentY * depth * 0.7).toFixed(3)}px`);
        });

        if (Math.abs(targetX - currentX) > 0.001 || Math.abs(targetY - currentY) > 0.001) {
            requestAnimationFrame(applyMotion);
        } else {
            ticking = false;
        }
    };

    const updateTarget = event => {
        const width = window.innerWidth || 1;
        const height = window.innerHeight || 1;
        targetX = (event.clientX / width - 0.5) * 2;
        targetY = (event.clientY / height - 0.5) * 2;
        if (!ticking) {
            ticking = true;
            requestAnimationFrame(applyMotion);
        }
    };

    const resetTarget = () => {
        targetX = 0;
        targetY = 0;
        if (!ticking) {
            ticking = true;
            requestAnimationFrame(applyMotion);
        }
    };

    window.addEventListener("pointermove", updateTarget, { passive: true });
    window.addEventListener("pointerleave", resetTarget);
}
