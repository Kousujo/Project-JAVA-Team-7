# Project-JAVA-Team-7
low cortisol only

# HƯỚNG DẪN SỬ DỤNG GIT & QUY TRÌNH LÀM VIỆC NHÓM (TEAM 7)

*Dành cho thành viên dự án Project-JAVA-Team-7*

## I. Khái niệm (Core Concepts)

Trước khi bắt đầu, cần hiểu Git quản lý code thông qua 3 khu vực:

1. **Working Directory:** Thư mục trên máy tính (nơi anh em gõ code).
2. **Staging Area:** Khu vực đệm, nơi chứa những thay đổi đã được chọn để chuẩn bị lưu.
3. **Repository (Local & Remote):** Nơi lưu trữ chính thức các phiên bản code (Local: ở máy cá nhân, Remote: ở trên GitHub).

## II. Tra cứu lệnh Git (Command Reference)

### 1. Nhóm lệnh khởi tạo và đồng bộ

| Lệnh      | Cú pháp (Syntax)              | Chức năng                                             |
|-----------|-------------------------------|-------------------------------------------------------|                     
| **Clone** | `git clone <url>`             | Tải toàn bộ dự án từ GitHub về máy tính lần đầu tiên. |
| **Pull**  | `git pull origin <tên_nhánh>` | Cập nhật code mới nhất từ trên GitHub về máy mình.    |
| **Push**  | `git push origin <tên_nhánh>` | Đẩy code đã lưu từ máy mình lên GitHub.               |

### 2. Nhóm lệnh làm việc hàng ngày

| Lệnh       | Cú pháp (Syntax)           | Chức năng                                                    |
|------------|----------------------------|--------------------------------------------------------------|
| **Status** | `git status`               | Kiểm tra trạng thái: file nào đã sửa, file nào chưa được lưu.|
| **Add**    | `git add <tên_file>`       | Đưa file vào "Staging Area" (Chuẩn bị đóng gói).             |
| **Commit** | `git commit -m "Lời nhắn"` | Lưu chính thức các thay đổi vào lịch sử kèm ghi chú rõ ràng. |

### 3. Nhóm lệnh quản lý Nhánh (Branching)

| Lệnh           | Cú pháp (Syntax)              | Chức năng                                            |
|----------------|-------------------------------|------------------------------------------------------|
| **Branch**     | `git branch`                  | Xem danh sách các nhánh đang có.                     |
| **Checkout**   | `git checkout <tên_nhánh>`    | Di chuyển từ nhánh này sang nhánh khác.              |
| **New Branch** | `git checkout -b <tên_nhánh>` | Tạo một nhánh mới và di chuyển sang đó ngay lập tức. |

---

## III. Quy trình phối hợp nhóm (Team Workflow)

Để đảm bảo dự án vận hành ổn định, mỗi thành viên tuân thủ quy trình 5 bước sau:

### Bước 1: Chuẩn bị môi trường

Lần đầu nhận dự án, mở Terminal và thực hiện:

`git clone https://github.com/Kousujo/Project-JAVA-Team-7.git`


### Bước 2: Tạo nhánh làm việc cá nhân

Tuyệt đối không code trực tiếp trên nhánh `main`. Mỗi người tự tạo nhánh riêng:

git checkout -b dev-<tên_thành_viên>
# Ví dụ: git checkout -b dev-tuan


### Bước 3: Cập nhật và Viết code

Trước khi code, luôn Pull để tránh xung đột:

`git pull origin main`


### Bước 4: Lưu và Đẩy code

Sau khi hoàn thành một tính năng hoặc bài tập:

```bash

git add . (Đưa toàn bộ file vào "Staging Area")
git commit -m "[Module_Name] - Nội dung thay đổi chi tiết"
git push origin dev-<tên_thành_viên>

```

### Bước 5: Hợp nhất (Merge)

Sau khi Push, lên giao diện GitHub web để tạo **Pull Request (PR)**. Manager (@Kousujo) sẽ review code và thực hiện Merge vào nhánh `main` nếu code đạt yêu cầu.

## IV. Quy tắc đặt tên và Ghi chú (Convention)

1. **Nhánh (Branch):**
* Nhánh tính năng: `feature/<tên_tính_năng>`
* Nhánh cá nhân: `dev/<tên_thành_viên>`


2. **Lời nhắn Commit:**
* Cấu trúc: `[Thư mục/Loại file] - Mô tả ngắn gọn bằng tiếng Việt có dấu hoặc tiếng Anh.`
* Ví dụ: `[src/DSA] - Hoàn thành giải thuật Sắp xếp nổi bọt`

## V. Xử lý sự cố (Troubleshooting)

1. **Khi gặp Conflict (Xung đột):** Git báo không thể Merge.
* *Cách xử lý:* Mở VS Code, tìm các đoạn code được đánh dấu `<<<< HEAD`, thảo luận với đồng đội để chọn code đúng nhất, sau đó Add và Commit lại.
2. **Gõ sai lệnh:** Sử dụng `git status` để xem hướng dẫn gợi ý từ Git.
3. **Mất code:** Code đã Commit thì không bao giờ mất. Liên hệ Manager để dùng lệnh `git reflog` khôi phục.
