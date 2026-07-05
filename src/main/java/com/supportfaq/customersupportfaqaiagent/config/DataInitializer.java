package com.supportfaq.customersupportfaqaiagent.config;

import com.supportfaq.customersupportfaqaiagent.dto.FaqRequest;
import com.supportfaq.customersupportfaqaiagent.entity.Category;
import com.supportfaq.customersupportfaqaiagent.repository.CategoryRepository;
import com.supportfaq.customersupportfaqaiagent.repository.FaqRepository;
import com.supportfaq.customersupportfaqaiagent.service.FaqService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final CategoryRepository categoryRepository;
    private final FaqRepository faqRepository;
    private final FaqService faqService;

    public DataInitializer(CategoryRepository categoryRepository, FaqRepository faqRepository,
                           FaqService faqService) {
        this.categoryRepository = categoryRepository;
        this.faqRepository = faqRepository;
        this.faqService = faqService;
    }

    @Override
    public void run(ApplicationArguments args) {
        seedCategories();
        seedFaqs();
    }

    private void seedCategories() {
        createCategory("Account", "الحساب", "Account access and profile support");
        createCategory("Security", "الأمان", "Passwords, verification, and account protection");
        createCategory("Orders", "الطلبات", "Order status, changes, cancellation, and invoices");
        createCategory("Billing", "الفواتير", "Payments, invoices, subscriptions, and billing questions");
        createCategory("Payment", "الدفع", "Payment methods, declined payments, and charges");
        createCategory("Delivery", "التوصيل", "Delivery, shipping, address changes, and tracking");
        createCategory("Refund", "الاسترجاع", "Refunds, returns, replacements, and cancellations");
        createCategory("Technical Support", "الدعم الفني", "Technical issues and troubleshooting");
        createCategory("General", "عام", "General customer support questions");
    }

    private void seedFaqs() {
        createFaq("How can I reset my password?",
                "Click Forgot Password on the login page, enter your email address, and follow the reset instructions sent to you.",
                "Account", "EN", "password, reset, forgot password, login, account");
        createFaq("I did not receive the password reset email. What should I do?",
                "Check your spam or junk folder, confirm that you entered the correct email address, and request a new reset email after a few minutes. If it still does not arrive, contact support.",
                "Account", "EN", "password reset email, email not received, spam, login");
        createFaq("How can I change my email address?",
                "Go to your account settings, update your email address, and confirm the change from the verification email sent to the new address.",
                "Account", "EN", "change email, update email, account settings, verification");
        createFaq("How can I update my phone number?",
                "Open your account settings, edit the phone number, save the change, and complete any verification step shown on the screen.",
                "Account", "EN", "phone number, update phone, account settings, verification");
        createFaq("How do I delete my account?",
                "Submit an account deletion request from account settings or contact support. Some data may be retained when required for legal, billing, or security reasons.",
                "Account", "EN", "delete account, close account, remove account, privacy");
        createFaq("Why is my account locked?",
                "Accounts can be locked after repeated failed sign-in attempts or suspicious activity. Reset your password and contact support if you still cannot sign in.",
                "Security", "EN", "locked account, suspicious activity, failed login, security");
        createFaq("How can I make my account more secure?",
                "Use a strong unique password, keep your recovery email and phone updated, sign out from shared devices, and enable two-step verification if available.",
                "Security", "EN", "account security, strong password, two step verification, protect account");
        createFaq("What should I do if I suspect unauthorized access?",
                "Change your password immediately, review recent account activity, remove unknown sessions or devices, and contact support so we can help secure the account.",
                "Security", "EN", "unauthorized access, hacked account, suspicious activity, secure account");
        createFaq("How do I enable two-step verification?",
                "Open security settings, choose two-step verification, select your preferred method, and follow the on-screen setup instructions.",
                "Security", "EN", "two step verification, 2fa, security code, authentication");
        createFaq("How can I recover my account if I lost access to my phone?",
                "Use your recovery email or backup method if available. If you cannot verify ownership, create a support ticket with details about the account.",
                "Security", "EN", "lost phone, account recovery, verification, backup method");
        createFaq("Where can I see my order status?",
                "Open the Orders section in your account and select the order. You will see its current status, tracking details, and available actions.",
                "Orders", "EN", "order status, my orders, track order, order details");
        createFaq("Can I cancel my order?",
                "You can cancel an order while it is still processing. If it has already shipped or been completed, you may need to request a return or contact support.",
                "Orders", "EN", "cancel order, order cancellation, processing, shipped");
        createFaq("Can I change my order after placing it?",
                "Order changes depend on the order status. If the order has not been processed yet, contact support as soon as possible with the requested change.",
                "Orders", "EN", "change order, edit order, modify order, order update");
        createFaq("How can I get an invoice or receipt?",
                "Open the order details page and choose Download Invoice or Receipt. If you cannot find it, contact support with the order number.",
                "Orders", "EN", "invoice, receipt, order invoice, payment receipt");
        createFaq("What does pending order status mean?",
                "Pending means the order is waiting for payment confirmation, stock confirmation, or internal review. The status will update automatically when processing continues.",
                "Orders", "EN", "pending order, order status, payment confirmation, review");
        createFaq("Why was my payment declined?",
                "Payments can be declined because of insufficient funds, incorrect card details, bank restrictions, expired cards, or security checks. Try another method or contact your bank.",
                "Payment", "EN", "payment declined, card declined, failed payment, bank");
        createFaq("Which payment methods do you accept?",
                "Accepted payment methods may include credit cards, debit cards, bank transfer, wallet payments, or cash on delivery depending on your region and checkout options.",
                "Payment", "EN", "payment methods, credit card, debit card, bank transfer, cash on delivery");
        createFaq("Why was I charged twice?",
                "A second charge may be a temporary authorization that your bank will release. If both charges are completed, contact support with the transaction details.",
                "Payment", "EN", "charged twice, duplicate charge, payment authorization, transaction");
        createFaq("How can I update my billing information?",
                "Go to Billing or Payment Settings, edit your billing address or payment method, and save the changes before your next invoice or purchase.",
                "Billing", "EN", "billing information, billing address, payment settings, invoice");
        createFaq("How do I manage my subscription?",
                "Open Billing or Subscription Settings to view your plan, renewal date, payment method, and available upgrade, downgrade, or cancellation options.",
                "Billing", "EN", "subscription, plan, renewal, cancel subscription, billing");
        createFaq("How do I cancel a subscription?",
                "Go to Subscription Settings and choose Cancel. The service may remain active until the end of the current billing period unless stated otherwise.",
                "Billing", "EN", "cancel subscription, stop renewal, plan cancellation, billing");
        createFaq("How can I track my delivery?",
                "Open the order details page and use the tracking number or tracking link. Delivery updates may take a few hours to appear after shipment.",
                "Delivery", "EN", "track delivery, tracking number, shipment, delivery status");
        createFaq("My tracking link is not updating. What should I do?",
                "Tracking can take time to update after shipment. If there is no update for more than two business days, contact support with your order number.",
                "Delivery", "EN", "tracking not updating, shipment delay, delivery tracking, order number");
        createFaq("Can I change my delivery address?",
                "Address changes are possible only before the order is shipped. Contact support immediately with the correct address and order number.",
                "Delivery", "EN", "change delivery address, shipping address, order address, shipment");
        createFaq("What should I do if my package is late?",
                "Check the tracking page first. If the estimated delivery date has passed, contact support with your order number so we can investigate.",
                "Delivery", "EN", "late package, delayed delivery, missing shipment, tracking");
        createFaq("What should I do if my package says delivered but I did not receive it?",
                "Check nearby locations, reception, building security, and neighbors. If you still cannot find it, contact support with the order number.",
                "Delivery", "EN", "delivered not received, missing package, delivery issue, order number");
        createFaq("How can I return an item?",
                "Open the order details page, choose Return if available, select a reason, and follow the instructions. If the return option is missing, contact support.",
                "Refund", "EN", "return item, start return, return request, order");
        createFaq("When will I receive my refund?",
                "Refund timing depends on the payment method and bank processing time. After the refund is approved, it may take several business days to appear.",
                "Refund", "EN", "refund status, refund time, money back, payment method");
        createFaq("Can I exchange an item instead of returning it?",
                "Exchanges depend on product availability and policy. If exchange is not available, you can return the item and place a new order.",
                "Refund", "EN", "exchange item, replacement, return, product availability");
        createFaq("What if I received a damaged or wrong item?",
                "Take clear photos of the item and package, keep all labels, and contact support with your order number so we can arrange a replacement, return, or refund.",
                "Refund", "EN", "damaged item, wrong item, replacement, refund, order issue");
        createFaq("Why is my refund still pending?",
                "A pending refund usually means it has been approved but is still being processed by the payment provider or bank. Contact support if it exceeds the expected timeline.",
                "Refund", "EN", "pending refund, refund delay, bank processing, payment provider");
        createFaq("The website is not loading. What can I try?",
                "Refresh the page, check your internet connection, clear browser cache, disable conflicting extensions, or try another browser or device.",
                "Technical Support", "EN", "website not loading, browser issue, cache, internet connection");
        createFaq("Why am I seeing an error message?",
                "Copy the error message, note what you were trying to do, refresh the page, and contact support if the issue continues.",
                "Technical Support", "EN", "error message, technical issue, troubleshooting, support");
        createFaq("The app is slow. How can I fix it?",
                "Close unused tabs or apps, check your connection, update the app or browser, and try again. If it remains slow, contact support with details.",
                "Technical Support", "EN", "app slow, performance, browser, connection");
        createFaq("How do I clear browser cache?",
                "Open your browser settings, find Privacy or History, choose Clear Browsing Data, select cached files, and reload the website.",
                "Technical Support", "EN", "clear cache, browser cache, browsing data, reload");
        createFaq("How can I contact customer support?",
                "You can contact support through WhatsApp or by creating a support ticket from the Support Tickets section.",
                "General", "EN", "support, contact, whatsapp, ticket, human support");
        createFaq("What information should I include in a support ticket?",
                "Include your name, email, order number if available, a clear description of the issue, screenshots, and any error messages.",
                "General", "EN", "support ticket, information, order number, screenshot, error");
        createFaq("How long does support take to reply?",
                "Response time depends on request volume and priority. Urgent account, payment, or delivery issues are reviewed as quickly as possible.",
                "General", "EN", "support reply time, response time, ticket, priority");

        createFaq("كيف أغير كلمة المرور؟",
                "اضغط على خيار نسيت كلمة المرور في صفحة تسجيل الدخول، أدخل بريدك الإلكتروني، ثم اتبع تعليمات إعادة التعيين.",
                "Account", "AR", "كلمة المرور، إعادة تعيين، تسجيل الدخول، الحساب");
        createFaq("لم يصلني بريد إعادة تعيين كلمة المرور، ماذا أفعل؟",
                "تحقق من مجلد الرسائل غير المرغوبة، وتأكد من كتابة البريد الصحيح، ثم اطلب رسالة جديدة بعد بضع دقائق. إذا استمرت المشكلة تواصل مع الدعم.",
                "Account", "AR", "إعادة كلمة المرور، البريد الإلكتروني، لم يصل، تسجيل الدخول");
        createFaq("كيف أحدث بيانات حسابي؟",
                "افتح إعدادات الحساب، عدل البيانات المطلوبة مثل الاسم أو البريد أو رقم الهاتف، ثم احفظ التغييرات وأكمل التحقق إن ظهر لك.",
                "Account", "AR", "تحديث الحساب، بيانات الحساب، البريد، الهاتف");
        createFaq("لماذا تم قفل حسابي؟",
                "قد يتم قفل الحساب بسبب محاولات دخول فاشلة متكررة أو نشاط غير معتاد. أعد تعيين كلمة المرور أو تواصل مع الدعم إذا لم تتمكن من الدخول.",
                "Security", "AR", "قفل الحساب، نشاط مشبوه، تسجيل الدخول، الأمان");
        createFaq("كيف أجعل حسابي أكثر أمانا؟",
                "استخدم كلمة مرور قوية وفريدة، حدث بريد ورقم الاسترداد، سجل الخروج من الأجهزة المشتركة، وفعل التحقق بخطوتين إذا كان متاحا.",
                "Security", "AR", "أمان الحساب، كلمة مرور قوية، التحقق بخطوتين");
        createFaq("ماذا أفعل إذا ظننت أن حسابي مخترق؟",
                "غير كلمة المرور فورا، راجع نشاط الحساب، أزل الأجهزة أو الجلسات غير المعروفة، ثم تواصل مع الدعم للمساعدة في حماية الحساب.",
                "Security", "AR", "حساب مخترق، دخول غير مصرح، نشاط مشبوه، حماية الحساب");
        createFaq("كيف أتابع حالة طلبي؟",
                "افتح قسم الطلبات في حسابك واختر الطلب المطلوب. ستظهر لك الحالة الحالية ورابط التتبع والإجراءات المتاحة.",
                "Orders", "AR", "حالة الطلب، تتبع الطلب، طلباتي، تفاصيل الطلب");
        createFaq("هل يمكنني إلغاء الطلب؟",
                "يمكن إلغاء الطلب إذا كان ما زال قيد المعالجة. إذا تم شحنه أو إكماله فقد تحتاج إلى طلب إرجاع أو التواصل مع الدعم.",
                "Orders", "AR", "إلغاء الطلب، حالة الطلب، قيد المعالجة، الشحن");
        createFaq("هل يمكنني تعديل طلبي بعد إرساله؟",
                "يعتمد تعديل الطلب على حالته. إذا لم تتم معالجته بعد، تواصل مع الدعم بسرعة واذكر رقم الطلب والتعديل المطلوب.",
                "Orders", "AR", "تعديل الطلب، تغيير الطلب، رقم الطلب");
        createFaq("كيف أحصل على فاتورة أو إيصال؟",
                "افتح تفاصيل الطلب واختر تحميل الفاتورة أو الإيصال. إذا لم تجد الخيار، تواصل مع الدعم واذكر رقم الطلب.",
                "Orders", "AR", "فاتورة، إيصال، رقم الطلب، الدفع");
        createFaq("لماذا فشلت عملية الدفع؟",
                "قد تفشل عملية الدفع بسبب نقص الرصيد أو خطأ في بيانات البطاقة أو قيود من البنك أو انتهاء صلاحية البطاقة. جرب طريقة أخرى أو تواصل مع البنك.",
                "Payment", "AR", "فشل الدفع، رفض البطاقة، البنك، طريقة الدفع");
        createFaq("ما طرق الدفع المتاحة؟",
                "قد تشمل طرق الدفع البطاقات البنكية أو التحويل أو المحافظ الإلكترونية أو الدفع عند الاستلام حسب منطقتك وخيارات صفحة الدفع.",
                "Payment", "AR", "طرق الدفع، بطاقة، تحويل، دفع عند الاستلام");
        createFaq("لماذا تم خصم المبلغ مرتين؟",
                "قد يكون الخصم الثاني تفويضا مؤقتا من البنك ويتم إلغاؤه تلقائيا. إذا كان الخصمان مكتملين، تواصل مع الدعم وأرسل تفاصيل العملية.",
                "Payment", "AR", "خصم مرتين، عملية مكررة، تفويض بنكي، الدفع");
        createFaq("كيف أعدل بيانات الفوترة؟",
                "افتح إعدادات الفوترة أو الدفع، عدل عنوان الفوترة أو طريقة الدفع، ثم احفظ التغييرات قبل الفاتورة أو العملية التالية.",
                "Billing", "AR", "الفوترة، عنوان الفوترة، طريقة الدفع، الفاتورة");
        createFaq("كيف ألغي الاشتراك؟",
                "افتح إعدادات الاشتراك واختر إلغاء. قد تبقى الخدمة فعالة حتى نهاية فترة الفوترة الحالية حسب سياسة الاشتراك.",
                "Billing", "AR", "إلغاء الاشتراك، إيقاف التجديد، الخطة، الفوترة");
        createFaq("كيف أتتبع الشحنة؟",
                "افتح تفاصيل الطلب واستخدم رقم التتبع أو رابط التتبع. قد تحتاج تحديثات الشحن إلى عدة ساعات حتى تظهر.",
                "Delivery", "AR", "تتبع الشحنة، رقم التتبع، التوصيل، الشحن");
        createFaq("رابط التتبع لا يتحدث، ماذا أفعل؟",
                "قد يتأخر تحديث التتبع بعد الشحن. إذا لم يظهر أي تحديث لأكثر من يومي عمل، تواصل مع الدعم واذكر رقم الطلب.",
                "Delivery", "AR", "التتبع لا يتحدث، تأخر الشحنة، رقم الطلب");
        createFaq("هل يمكنني تغيير عنوان التوصيل؟",
                "يمكن تغيير العنوان فقط قبل شحن الطلب. تواصل مع الدعم فورا وأرسل رقم الطلب والعنوان الصحيح.",
                "Delivery", "AR", "تغيير عنوان التوصيل، عنوان الشحن، رقم الطلب");
        createFaq("ماذا أفعل إذا تأخرت الشحنة؟",
                "تحقق من صفحة التتبع أولا. إذا مر تاريخ التسليم المتوقع، تواصل مع الدعم واذكر رقم الطلب للتحقق من المشكلة.",
                "Delivery", "AR", "تأخر الشحنة، تأخر التوصيل، رقم الطلب، التتبع");
        createFaq("تظهر الشحنة أنها وصلت لكنني لم أستلمها، ماذا أفعل؟",
                "تحقق من الأماكن القريبة والاستقبال وحارس المبنى والجيران. إذا لم تجدها، تواصل مع الدعم واذكر رقم الطلب.",
                "Delivery", "AR", "تم التوصيل ولم أستلم، شحنة مفقودة، رقم الطلب");
        createFaq("كيف أرجع منتجا؟",
                "افتح تفاصيل الطلب واختر إرجاع إذا كان الخيار متاحا، ثم اختر السبب واتبع التعليمات. إذا لم يظهر الخيار، تواصل مع الدعم.",
                "Refund", "AR", "إرجاع منتج، طلب إرجاع، المرتجعات، الطلب");
        createFaq("متى يصلني مبلغ الاسترداد؟",
                "يعتمد وقت الاسترداد على طريقة الدفع والبنك. بعد الموافقة على الاسترداد قد يستغرق ظهوره عدة أيام عمل.",
                "Refund", "AR", "استرداد، وقت الاسترداد، رجوع المبلغ، البنك");
        createFaq("هل يمكنني استبدال المنتج بدلا من إرجاعه؟",
                "يعتمد الاستبدال على توفر المنتج والسياسة. إذا لم يكن الاستبدال متاحا، يمكنك إرجاع المنتج وطلب منتج جديد.",
                "Refund", "AR", "استبدال، إرجاع، منتج بديل، توفر المنتج");
        createFaq("استلمت منتجا تالفا أو خاطئا، ماذا أفعل؟",
                "التقط صورا واضحة للمنتج والتغليف واحتفظ بالملصقات، ثم تواصل مع الدعم برقم الطلب لترتيب الاستبدال أو الإرجاع أو الاسترداد.",
                "Refund", "AR", "منتج تالف، منتج خاطئ، استبدال، استرداد، رقم الطلب");
        createFaq("الموقع لا يعمل، ماذا أجرب؟",
                "حدث الصفحة، تحقق من اتصال الإنترنت، امسح ذاكرة التخزين المؤقت، أوقف الإضافات المتعارضة، أو جرب متصفحا أو جهازا آخر.",
                "Technical Support", "AR", "الموقع لا يعمل، مشكلة المتصفح، الكاش، اتصال الإنترنت");
        createFaq("لماذا تظهر لي رسالة خطأ؟",
                "انسخ رسالة الخطأ، واكتب ما كنت تحاول فعله، ثم حدث الصفحة. إذا استمرت المشكلة تواصل مع الدعم وأرسل التفاصيل.",
                "Technical Support", "AR", "رسالة خطأ، مشكلة فنية، الدعم الفني، تفاصيل");
        createFaq("التطبيق بطيء، كيف أصلح المشكلة؟",
                "أغلق الصفحات أو التطبيقات غير المستخدمة، تحقق من الاتصال، حدث التطبيق أو المتصفح، ثم أعد المحاولة. إذا استمر البطء تواصل مع الدعم.",
                "Technical Support", "AR", "بطء التطبيق، الأداء، المتصفح، الاتصال");
        createFaq("كيف أتواصل مع الدعم؟",
                "يمكنك التواصل مع الدعم عبر واتساب أو إنشاء تذكرة من قسم تذاكر الدعم.",
                "General", "AR", "الدعم، واتساب، تذكرة دعم، التواصل");
        createFaq("ما المعلومات المطلوبة في تذكرة الدعم؟",
                "اكتب الاسم والبريد الإلكتروني ورقم الطلب إن وجد، واشرح المشكلة بوضوح، وأرفق لقطات شاشة أو رسائل الخطأ إن توفرت.",
                "General", "AR", "تذكرة دعم، رقم الطلب، لقطة شاشة، رسالة خطأ");
        createFaq("كم يستغرق رد الدعم؟",
                "يعتمد وقت الرد على حجم الطلبات والأولوية. تتم مراجعة مشاكل الحساب والدفع والتوصيل العاجلة بأسرع وقت ممكن.",
                "General", "AR", "وقت الرد، الدعم، الأولوية، تذكرة");
    }

    private void createCategory(String nameEnglish, String nameArabic, String description) {
        Category category = categoryRepository.findByNameEnglishIgnoreCase(nameEnglish).orElseGet(Category::new);
        if (category.getId() != null
                && nameArabic.equals(category.getNameArabic())
                && description.equals(category.getDescription())
                && "ACTIVE".equalsIgnoreCase(category.getStatus())) {
            return;
        }
        category.setNameEnglish(nameEnglish);
        category.setNameArabic(nameArabic);
        category.setDescription(description);
        category.setStatus("ACTIVE");
        categoryRepository.save(category);
    }

    private void createFaq(String question, String answer, String category, String language, String keywords) {
        if (faqRepository.existsByQuestionIgnoreCase(question)) {
            return;
        }
        FaqRequest request = new FaqRequest();
        request.setQuestion(question);
        request.setAnswer(answer);
        request.setCategory(category);
        request.setLanguage(language);
        request.setStatus("ACTIVE");
        request.setPriority("NORMAL");
        request.setKeywords(keywords);
        faqService.addFaq(request);
    }
}
