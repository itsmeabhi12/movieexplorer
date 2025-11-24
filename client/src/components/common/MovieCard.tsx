import Image from "next/image";

interface MovieCardProps {
  id: number;
  title: string;
  posterPath: string;
  showDeleteButton?: boolean;
  buttonIcon?: string;

  onClick: (id: number) => void;
  onDelete?: (id: number) => void;
}

export function MovieCard({
  id,
  title,
  posterPath,
  showDeleteButton,
  buttonIcon,
  onClick,
  onDelete,
}: MovieCardProps) {
  return (
    <div className="h-100 w-50 rounded-[12px] overflow-hidden relative group">
      <div className="relative w-full h-full">
        <Image
          src={process.env.NEXT_PUBLIC_TMDB_IMAGE_BASE_URL + posterPath}
          alt={title}
          fill
          sizes="(max-width: 640px) 50vw, (max-width: 1024px) 33vw, 25vw"
          className="object-cover"
        />

        <button
          className="absolute top-2 right-2 w-10 h-10 bg-red-200 text-white rounded-full flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-100"
          onClick={() => {
            onClick(id);
          }}
        >
          {buttonIcon ?? "❤️"}
        </button>

        {showDeleteButton && (
          <button
            className="absolute top-14 right-2 w-10 h-10 bg-red-200 text-white rounded-full flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-100"
            onClick={() => {
              onDelete?.(id);
            }}
          >
            {"🗑️"}
          </button>
        )}

        <div className="absolute bottom-0 left-0 right-0 p-2 bg-gradient-to-t from-black/70 to-transparent text-white">
          {title}
        </div>
      </div>
    </div>
  );
}
