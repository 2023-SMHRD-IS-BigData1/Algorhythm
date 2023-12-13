/*
window.addEventListener('DOMContentLoaded', event => {

    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }

});
*/


//window.addEventListener('DOMContentLoaded', event => {
//
//    // Toggle the side navigation
//    const sidebarToggle = document.body.querySelector('#sidebarToggle');
//    if (sidebarToggle) {
//        sidebarToggle.addEventListener('click', event => {
//            event.preventDefault();
//            document.body.classList.toggle('sb-sidenav-toggled');
//            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
//        });
//
//        // Check local storage for sidebar state on page load
//        const isSidebarToggled = localStorage.getItem('sb|sidebar-toggle') === 'true';
//        document.body.classList.toggle('sb-sidenav-toggled', isSidebarToggled);
//    }
//
//});

window.addEventListener('DOMContentLoaded', (event) => {
  const sidebarToggle = document.body.querySelector('#sidebarToggle');

  const toggleSidebar = () => {
    document.body.classList.toggle('sb-sidenav-toggled');
    sessionStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
  };

  if (sidebarToggle) {
    sidebarToggle.addEventListener('click', (event) => {
      event.preventDefault();
      toggleSidebar();
    });

    // 페이지 로드 시 세션 저장소에서 사이드바 상태 확인
    const isSidebarToggled = sessionStorage.getItem('sb|sidebar-toggle') === 'true';
    document.body.classList.toggle('sb-sidenav-toggled', isSidebarToggled);
  }

  // 링크 클릭 이벤트를 감지하고 사이드바 상태를 업데이트
  document.addEventListener('click', (event) => {
    const target = event.target.closest('a');
    if (target && target.getAttribute('href') && !target.getAttribute('href').startsWith('#')) {
      toggleSidebar();
    }
  });
});