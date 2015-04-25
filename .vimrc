"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
" => VIM user interface
"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
" Set 7 lines to the cursor - when moving vertically using j/k
set so=7

" Turn on the WiLd menu
set wildmenu

" Ignore compiled files
set wildignore=*.o,*~,*.pyc

"Always show current position
set ruler

" Height of the command bar
set cmdheight=2

" A buffer becomes hidden when it is abandoned
set hid

" Configure backspace so it acts as it should act
set backspace=eol,start,indent
set whichwrap+=<,>,h,l

" Ignore case when searching
set ignorecase


" Highlight search results
set hlsearch

" When searching try to be smart about cases 
set smartcase

" Makes search act like search in modern browsers
set incsearch

" Don't redraw while executing macros (good performance config)
set lazyredraw

" For regular expressions turn magic on
set magic

" Show matching brackets when text indicator is over them
set showmatch
" How many tenths of a second to blink when matching brackets
set mat=2

" No annoying sound on errors
set noerrorbells
set novisualbell
set t_vb=
set tm=500

set cindent
set nu
"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
" => Colors and Fonts
""""""""""""""""""""crcrcrcrcrcr"""""""""""""""""""""""""""""""""""""""""""
" Enable syntax highlighting
syntax enable

colorscheme desert
set background=dark

nmap ,<space> :noh<CR><CR>
map w <PageUp>  
map s <PageDown>
imap <C-e> <esc>$i<right>
imap <C-a> <esc>0i
nmap <C-e> <esc>$i<right>
nmap <C-a> <esc>0i
